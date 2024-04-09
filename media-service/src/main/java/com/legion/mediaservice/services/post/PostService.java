package com.legion.mediaservice.services.post;

import com.legion.mediaservice.dto.PostDTO;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;


public interface PostService {

    Page<PostDTO> listPosts(UUID creatorId, String postContent, Integer page, Integer pageSize);

    Optional<PostDTO> getPostById(UUID id);

    PostDTO saveNewPost(String uid, PostDTO postDTO);

    Optional<PostDTO> updatePostById(UUID postId, PostDTO post);

    Boolean deleteById(UUID postId);

    Optional<PostDTO> patchPostById(UUID postId, PostDTO postDTO);
}
