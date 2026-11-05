package com.group.jwtexample.domain.post.dto;

import com.group.jwtexample.domain.post.entity.Post;

public record PostResponse(
        Long id,
        String title,
        String content,
        Long writerId
) {

    public static PostResponse from(Post post) {
        return new PostResponse(post.getId(), post.getTitle(), post.getContent(), post.getWriterId());
    }
}
