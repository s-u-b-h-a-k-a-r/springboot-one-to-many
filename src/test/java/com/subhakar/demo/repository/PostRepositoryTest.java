package com.subhakar.demo.repository;

import com.github.javafaker.Faker;
import com.subhakar.demo.model.Post;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ExtendWith(SpringExtension.class)
/**
 Uncomment to run on the original database instead of in-memory h2 database.
 @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
 **/
class PostRepositoryTest {
    private Post post;
    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void setUp() {
        Faker faker = new Faker();
        post = Post.builder()
                .title(faker.harryPotter().character())
                .description(faker.harryPotter().book())
                .published(true)
                .build();
    }

    @Test
    public void givenPost_whenPostSaved_thenReturnTheId() {
        Post _post = postRepository.save(post);
        assertEquals(1L, _post.getId());
    }

    @Test
    public void givenPost_whenPostSaved_thenReturnTheAddedPost() {
        postRepository.save(post);
        Optional<Post> _post = postRepository.findById(1L);
        assertEquals(post.getDescription(), _post.get().getDescription());
        assertEquals(post.getTitle(), _post.get().getTitle());
        assertEquals(post.isPublished(), _post.get().isPublished());
    }

    @Test
    public void givenPost_whenPostSaved_whenPostDeleted_ThenReturnNull() {
        Post _post = postRepository.save(post);
        assertEquals(1L, _post.getId());
        postRepository.deleteById(_post.getId());
        Optional<Post> optional = postRepository.findById(_post.getId());
        assertEquals(Optional.empty(), optional);
    }

    @AfterEach
    void tearDown() {
        post = null;
        postRepository.deleteAll();
    }
}