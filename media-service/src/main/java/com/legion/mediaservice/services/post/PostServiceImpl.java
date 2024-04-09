package com.legion.mediaservice.services.post;

import com.legion.mediaservice.controller.NotFoundException;
import com.legion.mediaservice.entities.User;
import com.legion.mediaservice.entities.image.PostImage;
import com.legion.mediaservice.entities.Post;
import com.legion.mediaservice.mappers.PostImageMapper;
import com.legion.mediaservice.mappers.PostMapper;
import com.legion.mediaservice.dto.ImageDTO;
import com.legion.mediaservice.dto.PostDTO;
import com.legion.mediaservice.repositories.PostRepository;
import com.legion.mediaservice.services.user.UserService;
import com.legion.mediaservice.utils.PageRequestBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService {

    private final PostMapper postMapper;
    private final PostImageMapper imageMapper;
    private final PostRepository postRepository;
    private final UserService userService;


    @Override
    public PostDTO saveNewPost(String uid, PostDTO postDTO) {

        User user = userService.getUserByUid(uid).orElseThrow(NotFoundException::new);

        Post post = postMapper.postDtoToPost(postDTO);

        post.setCreator(user);

        List<ImageDTO> imageDTOs = postDTO.getImages();
        if (imageDTOs != null && !imageDTOs.isEmpty()) {
            for (ImageDTO imageDTO : imageDTOs) {
                PostImage postImage = imageMapper.imageDtoToImage(imageDTO);
                post.addImage(postImage);
            }
        }

        Post savedPost = postRepository.save(post);
        return postMapper.postToPostDTO(savedPost);

    }

    @Override
    public Page<PostDTO> listPosts(UUID creatorId, String postContent, Integer page, Integer pageSize) {

        PageRequest pageRequest = new PageRequestBuilder().buildPageRequest(page, pageSize);
        Page<Post> postPage;

        if(creatorId != null && StringUtils.hasText(postContent))
            postPage = listPostsByCreatorIdAndContent(creatorId, postContent, pageRequest);

        else if(creatorId != null)
            postPage = listPostsByCreatorId(creatorId, pageRequest);

        else if(StringUtils.hasText(postContent))
            postPage = listPostsByContent(postContent, pageRequest);

        else
            postPage = postRepository.findAll(pageRequest);

        return postPage.map(postMapper::postToPostDTO);
    }


    private Page<Post> listPostsByContent(String postContent, PageRequest pageRequest) {
        return postRepository.findByContentContains(postContent, pageRequest);
    }
    private Page<Post> listPostsByCreatorId(UUID creatorId, PageRequest pageRequest) {
        return postRepository.findByCreator_Id(creatorId, pageRequest);
    }
    private Page<Post> listPostsByCreatorIdAndContent(UUID creatorId, String postContent, PageRequest pageRequest) {
        return postRepository.findByCreator_IdEqualsAndContentContains(creatorId, postContent, pageRequest);
    }


    @Override
    public Optional<PostDTO> getPostById(UUID id) {
        return Optional.ofNullable(
            postMapper.postToPostDTO(this.postRepository.findById(id).orElse(null)));
    }

    @Override
    public Optional<PostDTO> updatePostById(UUID postId, PostDTO dto) {
        AtomicReference<Optional<PostDTO>> atomicReference = new AtomicReference<>();

        postRepository.findById(postId).ifPresentOrElse(foundPost -> {
            foundPost.setContent(dto.getContent());
            atomicReference.set(Optional.of(postMapper.postToPostDTO(postRepository.save(foundPost))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();
    }

    @Override
    public Boolean deleteById(UUID postId) {
        if (postRepository.existsById(postId)) {
            postRepository.deleteById(postId);
            return true;
        }
        return false;
    }

    @Override
    public Optional<PostDTO> patchPostById(UUID postId, PostDTO postDTO) {
        return Optional.empty();
    }

    public boolean isOwner(Principal principal, Post entity) {
        if (entity != null) {
            String authenticatedUid = principal.getName();
            return authenticatedUid.equals(entity.getCreator().getUid());
        }
        return false;
    }
}
