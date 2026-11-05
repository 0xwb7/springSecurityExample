package com.group.jwtexample.domain.auth.repository;

import java.util.Optional;

import com.group.jwtexample.domain.auth.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByUserId(Long userId);
}
