package com.group.jwtexample.domain.post.controller;

import com.group.jwtexample.domain.post.dto.PostCreateRequest;
import com.group.jwtexample.domain.post.dto.PostResponse;
import com.group.jwtexample.domain.post.service.PostService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponse createPost(@AuthenticationPrincipal Long userId,
                                   @Valid @RequestBody PostCreateRequest request) {
        return postService.createPost(userId, request);
    }

    @GetMapping
    public List<PostResponse> getPosts() {
        return postService.getPosts();
    }

    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable @Positive(message = "게시글 id는 양수여야 합니다.") Long postId,
                           @AuthenticationPrincipal Long userId) {
        postService.deletePost(postId, userId);
    }
}
