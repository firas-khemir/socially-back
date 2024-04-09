package com.legion.commentservice.services;

import com.legion.commentservice.models.CommentDTO;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

public interface CommentService {

    Page<CommentDTO> listPostComments(UUID postId, Integer page, Integer pageSize);
    Page<CommentDTO> listUserComments(UUID userId, Integer page, Integer pageSize);
    Optional<CommentDTO> getCommentById(UUID id);

    CommentDTO saveNewComment(CommentDTO commentDTO);

    Optional<CommentDTO> updateCommentById(UUID commentId, String content);

    Boolean deleteById(UUID commentId);

}
