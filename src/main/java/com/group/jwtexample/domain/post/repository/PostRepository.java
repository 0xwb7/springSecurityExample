package com.group.jwtexample.domain.post.repository;

import com.group.jwtexample.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
