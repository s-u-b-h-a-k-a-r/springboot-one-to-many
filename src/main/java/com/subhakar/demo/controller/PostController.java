package com.subhakar.demo.controller;

import com.subhakar.demo.exception.ResourceNotFoundException;
import com.subhakar.demo.model.Post;
import com.subhakar.demo.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {
    private final PostRepository postRepository;

    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getAllPosts(@RequestParam(required = false) String title) {
        List<Post> posts = new ArrayList<>();
        if (title == null) {
            postRepository.findAll().forEach(new Consumer<Post>() {
                @Override
                public void accept(Post post) {
                    posts.add(post);
                }
            });
        } else {
            postRepository.findByTitleContaining(title).forEach(new Consumer<Post>() {
                @Override
                public void accept(Post post) {
                    posts.add(post);
                }
            });
        }
        if (posts.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable("id") long id) {
        Post post = postRepository.findById(id).orElseThrow(new Supplier<ResourceNotFoundException>() {
            @Override
            public ResourceNotFoundException get() {
                return new ResourceNotFoundException("Not found post with id = " + id);
            }
        });
        return ResponseEntity.ok(post);
    }

    @GetMapping("/posts/published")
    public ResponseEntity<List<Post>> getPostByPublished() {
        List<Post> posts = postRepository.findByPublished(true);
        if (posts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(posts);
    }

    @PostMapping("/posts")
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        Post _post = postRepository.save(post);
        return new ResponseEntity(_post, HttpStatus.CREATED);
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable("id") long id, @RequestBody Post post) {
        Post _post = postRepository.findById(id).orElseThrow(new Supplier<ResourceNotFoundException>() {
            @Override
            public ResourceNotFoundException get() {
                return new ResourceNotFoundException("Not found post with id = " + id);
            }
        });
        _post.setDescription(post.getDescription());
        _post.setTitle(post.getTitle());
        _post.setPublished(post.isPublished());
        return ResponseEntity.ok(_post);
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<HttpStatus> deleteByPostId(@PathVariable("id") long id) {
        postRepository.findById(id).orElseThrow(new Supplier<ResourceNotFoundException>() {
            @Override
            public ResourceNotFoundException get() {
                return new ResourceNotFoundException("Not found post with id = " + id);
            }
        });
        postRepository.deleteById(id);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/posts")
    public ResponseEntity<HttpStatus> deleteAllPosts() {
        postRepository.deleteAll();
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }
}

