package com.legion.mediaservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.legion.mediaservice.entities.Post;
import com.legion.mediaservice.entities.User;
import com.legion.mediaservice.mappers.PostMapper;
import com.legion.mediaservice.dto.ImageDTO;
import com.legion.mediaservice.dto.PostDTO;
import com.legion.mediaservice.repositories.PostRepository;
import com.legion.mediaservice.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Slf4j
class PostControllerIT {
    @Autowired
    PostController postController;

    @Autowired
    UserRepository userRepository;
    @Autowired
    PostRepository postRepository;

    @Autowired
    PostMapper postMapper;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    @Transactional
    void testListPosts() {
        Page<PostDTO> posts = postController.listPosts(null,null, 0, 25);
        assertThat(posts.stream().count()).isEqualTo(3);
    }

    @Test
    @Transactional
    void testListCreatorPosts() {

        User user = userRepository.findAll().get(0);
        Page<PostDTO> posts = postController.listPosts(user.getId(),null, 0, 25);
        assertThat(posts.stream().count()).isEqualTo(1);
    }

    @Test
    @Transactional
    void testSearchPosts() {
        Page<PostDTO> testPost1 = postController.listPosts(null,"test-post1", 0, 25);
        assertThat(testPost1.stream().count()).isEqualTo(1);


        Page<PostDTO> allTestPosts = postController.listPosts(null,"test-post", 0, 25);
        assertThat(allTestPosts.stream().count()).isEqualTo(3);
    }

    @Test
    @Transactional
    void testListCreatorPostsWithSearch() {
        User user = userRepository.findAll().get(0);

        Page<PostDTO> posts = postController.listPosts(user.getId(),"test-post1", 0, 25);
        assertThat(posts.stream().count()).isEqualTo(1);
    }

    @Test
    @Transactional
    void testGetPostById() {
        Post post = postRepository.findAll().get(0);
        PostDTO foundPost = postController.getPostById(post.getId());
        assertThat(foundPost).isNotNull();
    }

    @Rollback
    @Transactional
    @Test
    void saveNewPostTest() {

        ImageDTO imageDTO1 = ImageDTO.builder()
            .hq_url("1")
            .mq_url("1")
            .lq_url("1")
            .build();

        ImageDTO imageDTO2 = ImageDTO.builder()
            .hq_url("2")
            .mq_url("2")
            .lq_url("2")
            .build();

        List<ImageDTO> imageList = new ArrayList<>();

        imageList.add(imageDTO1);
        imageList.add(imageDTO2);

        PostDTO postDTO = PostDTO.builder()
                .content("test-post1")
                .images(imageList)
                .build();

        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("me");

//        RequestBuilder requestBuilder = MockMvcRequestBuilders
//            .get(USER_ENDPOINT_URL)
//            .principal(mockPrincipal)
//            .accept(MediaType.APPLICATION_JSON);


        ResponseEntity<?> responseEntity = postController.createNewPost(postDTO, mockPrincipal);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");

        UUID savedUUID = UUID.fromString(locationUUID[4]);

//        Post post = postRepository.findById(savedUUID).get();
//        assertThat(post).isNotNull();
    }

    @Test
    void testDeleteByIDNotFound() {
        assertThrows(NotFoundException.class, () -> {
            postController.deletePost(UUID.randomUUID());
        });
    }

    @Rollback
    @Transactional
    @Test
    void deleteByIdFound() {
        Post post = postRepository.findAll().get(0);

        ResponseEntity<?> responseEntity = postController.deletePost(post.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        assertThat(postRepository.findById(post.getId()).isEmpty()).isTrue();
    }


    @Test
    void testUpdateNotFound() {
        assertThrows(NotFoundException.class, () -> {
          postController.updatePostByID(UUID.randomUUID(), PostDTO.builder().build());
        });
    }

    @Rollback
    @Transactional
    @Test
    void updateExistingPost() {

        Post post = postRepository.findAll().get(0);

        PostDTO postDTO = postMapper.postToPostDTO(post);
        final String postContent = "post-1-post-test";
        postDTO.setContent(postContent);

        ResponseEntity<?> responseEntity = postController.updatePostByID(post.getId(), postDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Post updatedPost = postRepository.findById(post.getId()).get();
        assertThat(updatedPost.getContent()).isEqualTo(postContent);
    }
}







