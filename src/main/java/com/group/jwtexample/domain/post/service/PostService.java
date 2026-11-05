package com.group.jwtexample.domain.post.service;
import java.util.List;

import com.group.jwtexample.domain.post.dto.PostCreateRequest;
import com.group.jwtexample.domain.post.dto.PostResponse;
import com.group.jwtexample.domain.post.entity.Post;
import com.group.jwtexample.domain.post.repository.PostRepository;
import com.group.jwtexample.global.exception.ErrorCode;
import com.group.jwtexample.global.exception.ForbiddenException;
import com.group.jwtexample.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public PostResponse createPost(Long userId, PostCreateRequest request) {
        Post post = new Post(request.title(), request.content(), userId);
        return PostResponse.from(postRepository.save(post));
    }

    public List<PostResponse> getPosts() {
        return postRepository.findAll().stream()
                .map(PostResponse::from)
                .toList();
    }

    @Transactional
    public void deletePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.POST_NOT_FOUND));

        // 인가: 로그인한 사람이 게시글 작성자인지 확인
        if (!post.isWrittenBy(userId)) {
            throw new ForbiddenException(ErrorCode.POST_FORBIDDEN);
        }

        postRepository.delete(post);
    }
}
