package com.subhakar.demo.service;

import com.subhakar.demo.model.Post;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

public interface PostService {
    List<Post> getAllPosts();

    List<Post> getAllPostsByTitleContaining(String title);

    Post getPostById(long id);

    List<Post> getPostByPublished();

    Post createPost(Post post);

    Post updatePost(long id, Post post);

    void deleteByPostId(long id);

    void deleteAllPosts();
}
