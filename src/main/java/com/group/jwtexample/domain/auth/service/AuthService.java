package com.group.jwtexample.domain.auth.service;

import com.group.jwtexample.domain.auth.dto.LoginRequest;
import com.group.jwtexample.domain.auth.dto.RefreshRequest;
import com.group.jwtexample.domain.auth.dto.SignupRequest;
import com.group.jwtexample.domain.auth.dto.TokenResponse;
import com.group.jwtexample.domain.auth.entity.RefreshToken;
import com.group.jwtexample.domain.auth.repository.RefreshTokenRepository;
import com.group.jwtexample.domain.user.entity.User;
import com.group.jwtexample.domain.user.repository.UserRepository;
import com.group.jwtexample.global.exception.ConflictException;
import com.group.jwtexample.global.exception.ErrorCode;
import com.group.jwtexample.global.exception.UnauthorizedException;
import com.group.jwtexample.global.jwt.JwtProvider;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public void signup(SignupRequest request) {
        String email = request.email();
        if (userRepository.findByEmail(email).isPresent()) {
            throw new ConflictException(ErrorCode.DUPLICATE_EMAIL);
        }

        try {
            userRepository.saveAndFlush(new User(email, passwordEncoder.encode(request.password())));
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException(ErrorCode.DUPLICATE_EMAIL, e);
        }
    }

    public TokenResponse login(LoginRequest request) {
        // 1. 이메일로 사용자 찾기
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new UnauthorizedException(ErrorCode.INVALID_CREDENTIALS));

        // 2. 비밀번호 확인
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new UnauthorizedException(ErrorCode.INVALID_CREDENTIALS);
        }

        // 3. 토큰 발급
        return issueTokens(user.getId());
    }

    public TokenResponse refresh(RefreshRequest request) {
        String refreshToken = request.refreshToken();

        // 1. Refresh Token 자체가 올바른지 확인 (서명, 만료, 종류)
        Long userId;
        try {
            userId = jwtProvider.getUserIdFromRefreshToken(refreshToken);
        } catch (JwtException | IllegalArgumentException e) {
            throw new UnauthorizedException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        // 2. DB에 저장된 최신 Refresh Token과 같은지 확인
        RefreshToken savedToken = refreshTokenRepository.findByUserId(userId)
                .orElseThrow(() -> new UnauthorizedException(ErrorCode.REFRESH_TOKEN_NOT_FOUND));

        if (!savedToken.getToken().equals(refreshToken)) {
            throw new UnauthorizedException(ErrorCode.REFRESH_TOKEN_REUSED);
        }

        // 3. 새 토큰 발급 (이전 Refresh Token은 덮어써져서 더 이상 못 씀)
        return issueTokens(userId);
    }

    private TokenResponse issueTokens(Long userId) {
        String accessToken = jwtProvider.createAccessToken(userId);
        String refreshToken = jwtProvider.createRefreshToken(userId);

        // 처음 로그인이면 새로 저장하고, 이미 있으면 새 토큰으로 덮어씁니다.
        RefreshToken savedToken = refreshTokenRepository.findByUserId(userId)
                .orElse(new RefreshToken(userId, refreshToken));

        savedToken.updateToken(refreshToken);
        refreshTokenRepository.save(savedToken);

        return TokenResponse.of(accessToken, refreshToken);
    }
}
