package com.subhakar.demo;

import com.github.javafaker.Faker;
import com.subhakar.demo.model.Comment;
import com.subhakar.demo.model.Post;
import com.subhakar.demo.repository.CommentRepository;
import com.subhakar.demo.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Override
    public void run(String... args) throws Exception {
        createPosts();
    }

    private void createPosts() {
        IntStream.range(1, 100).forEach(i -> {
            Faker faker = new Faker();
            Post post = Post.builder()
                    .title(faker.harryPotter().character())
                    .description(faker.harryPotter().book())
                    .published(true)
                    .build();
            Post _post = postRepository.save(post);
            commentRepository.save(
                    Comment.builder()
                            .content(faker.harryPotter().quote())
                            .post(_post)
                            .build()
            );
            commentRepository.save(
                    Comment.builder()
                            .content(faker.harryPotter().quote())
                            .post(_post)
                            .build()
            );
            commentRepository.save(
                    Comment.builder()
                            .content(faker.harryPotter().quote())
                            .post(_post)
                            .build()
            );
        });
    }
}
