package com.legion.mediaservice.controller;

import com.google.firebase.auth.FirebaseAuth;
import com.legion.mediaservice.dto.PostDTO;
import com.legion.mediaservice.services.post.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
public class PostController {

    public static final String POST_PATH = "/api/media/posts";
    public static final String POST_PATH_ID = POST_PATH + "/{postId}";

    private final PostService postService;

    private final FirebaseAuth firebaseAuth;

    @GetMapping(value = POST_PATH)
    public Page<PostDTO> listPosts(@RequestParam(required = false) UUID creatorId,
                                   @RequestParam(required = false) String postContent,
                                   @RequestParam(required = false) Integer page,
                                   @RequestParam(required = false) Integer pageSize) {

        return postService.listPosts(creatorId, postContent, page, pageSize);
    }

    @GetMapping(value = POST_PATH_ID)
    @PreAuthorize("@postServiceImpl.isOwner(authentication, #id)")
    public PostDTO getPostById(@PathVariable("postId") UUID id){
        return postService.getPostById(id).orElseThrow(NotFoundException::new);
    }

    @PostMapping(POST_PATH)
    public ResponseEntity<?> createNewPost(@Validated @RequestBody PostDTO dto, Principal principal){

        PostDTO savedPost = postService.saveNewPost(principal.getName(), dto);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", POST_PATH + "/" + savedPost.getId().toString());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping(POST_PATH_ID)
    public ResponseEntity<?> updatePostByID(@PathVariable("postId") UUID postId,
                                            @RequestBody PostDTO dto){

        if (postService.updatePostById(postId, dto).isEmpty()){
            throw new NotFoundException();
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(POST_PATH_ID)
    public ResponseEntity<?> deletePost(@PathVariable("postId") UUID postId){

        if (!postService.deleteById(postId)){
            throw new NotFoundException();
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
