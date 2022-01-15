package com.subhakar.demo.service;

import com.subhakar.demo.model.Post;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService{

    @Override
    public List<Post> getAllPosts() {
        return null;
    }

    @Override
    public List<Post> getAllPostsByTitleContaining(String title) {
        return null;
    }

    @Override
    public Post getPostById(long id) {
        return null;
    }

    @Override
    public List<Post> getPostByPublished() {
        return null;
    }

    @Override
    public Post createPost(Post post) {
        return null;
    }

    @Override
    public Post updatePost(long id, Post post) {
        return null;
    }

    @Override
    public void deleteByPostId(long id) {

    }

    @Override
    public void deleteAllPosts() {

    }
}
