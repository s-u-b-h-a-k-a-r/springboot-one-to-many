package com.subhakar.demo.controller;

import com.subhakar.demo.exception.ResourceNotFoundException;
import com.subhakar.demo.model.Comment;
import com.subhakar.demo.repository.CommentRepository;
import com.subhakar.demo.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Supplier;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CommentController {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @RequestMapping("/posts/{postId}/comments")
    public ResponseEntity<List<Comment>> getAllCommentsByPostId(@PathVariable("postId") long postId) {
        if (!postRepository.existsById(postId)) {
            throw new ResourceNotFoundException("Not found post with id = " + postId);
        }
        List<Comment> comments = commentRepository.findByPostId(postId);
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<Comment> createComment(@PathVariable("postId") long postId, @RequestBody Comment comment) {
        Comment _comment = postRepository.findById(postId).map(post -> {
            comment.setPost(post);
            return commentRepository.save(comment);
        }).orElseThrow(new Supplier<ResourceNotFoundException>() {
            @Override
            public ResourceNotFoundException get() {
                return new ResourceNotFoundException("Not found post with id = " + postId);
            }
        });
        return new ResponseEntity<>(_comment, HttpStatus.CREATED);
    }

    @GetMapping("/comments/{id}")
    public ResponseEntity<Comment> getCommentsById(@PathVariable("id") long id) {
        Comment _comment = commentRepository.findById(id).orElseThrow(new Supplier<ResourceNotFoundException>() {
            @Override
            public ResourceNotFoundException get() {
                return new ResourceNotFoundException("Not found comment with id = " + id);
            }
        });
        return ResponseEntity.ok(_comment);
    }

    @PutMapping("/api/comments/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable("id") long id, @RequestBody Comment comment) {
        Comment _comment = commentRepository.findById(id).orElseThrow(new Supplier<ResourceNotFoundException>() {
            @Override
            public ResourceNotFoundException get() {
                return new ResourceNotFoundException("Not found comment with id = " + id);
            }
        });
        _comment.setContent(comment.getContent());
        commentRepository.save(_comment);
        return ResponseEntity.ok(_comment);
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable("id") long id) {
        commentRepository.deleteById(id);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/posts/{postId}/comments")
    public ResponseEntity<HttpStatus> deleteAllCommentsOfPost(@PathVariable("postId") long postId) {
        if (!postRepository.existsById(postId)) {
            throw new ResourceNotFoundException("Not found Tutorial with id = " + postId);
        }
        commentRepository.deleteByPostId(postId);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }
}

