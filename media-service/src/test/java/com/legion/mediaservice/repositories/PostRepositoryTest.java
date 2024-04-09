package com.legion.mediaservice.repositories;

import com.legion.mediaservice.entities.User;
import com.legion.mediaservice.utils.BootstrapData;
import com.legion.mediaservice.entities.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@DataJpaTest
@Import({BootstrapData.class})
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Test
    void testSavePost() {

        User creator = mock(User.class);
        Post savedPost = Post.builder()
            .id(UUID.randomUUID())
            .content("test-post1")
            .creator(creator)
            .createdDate(LocalDateTime.now())
            .lastModifiedDate(LocalDateTime.now())
            .build();

        postRepository.flush();

        assertThat(savedPost).isNotNull();
        assertThat(savedPost.getId()).isNotNull();
    }
}
