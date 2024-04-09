package com.legion.commentservice.services;

import com.legion.commentservice.entities.Comment;
import com.legion.commentservice.mappers.CommentMapper;
import com.legion.commentservice.models.CommentDTO;
import com.legion.commentservice.repositories.CommentRepository;
import com.legion.commentservice.utils.PageRequestBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;


@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Override
    public Page<CommentDTO> listPostComments(UUID postId, Integer page, Integer pageSize) {
        PageRequest pageRequest = new PageRequestBuilder().buildPageRequest(page, pageSize);
        Page<Comment> commentsPage;

        commentsPage = commentRepository.findByPostId(postId, pageRequest);

        return commentsPage.map(commentMapper::commentToCommentDTO);
    }

    @Override
    public Page<CommentDTO> listUserComments(UUID userId, Integer page, Integer pageSize) {

        PageRequest pageRequest = new PageRequestBuilder().buildPageRequest(page, pageSize);
        Page<Comment> commentsPage;

        commentsPage = commentRepository.findByUserId(userId, pageRequest);

        return commentsPage.map(commentMapper::commentToCommentDTO);

    }

    @Override
    public Optional<CommentDTO> getCommentById(UUID id) {
        return Optional.ofNullable(commentMapper.commentToCommentDTO(commentRepository.findById(id).orElse(null)));
    }

    @Override
    public CommentDTO saveNewComment(CommentDTO commentDTO) {
        return commentMapper.commentToCommentDTO(
            commentRepository.save(commentMapper.commentDTOToComment(commentDTO)));
    }

    @Override
    public Optional<CommentDTO> updateCommentById(UUID commentId, String content) {

        AtomicReference<Optional<CommentDTO>> atomicReference = new AtomicReference<>();

        commentRepository.findById(commentId).ifPresentOrElse((comment) -> {
            comment.setContent(content);
            atomicReference.set(Optional.of(commentMapper.commentToCommentDTO(commentRepository.save(comment))));
        },() ->  atomicReference.set(Optional.empty()));

        return atomicReference.get();
    }

    @Override
    public Boolean deleteById(UUID commentId) {
       if (commentRepository.existsById(commentId)) {
           commentRepository.deleteById(commentId);
           return true;
       }
       return false;
    }
}
