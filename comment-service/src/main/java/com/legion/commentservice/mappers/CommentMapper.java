package com.legion.commentservice.mappers;

import com.legion.commentservice.entities.Comment;
import com.legion.commentservice.models.CommentDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    CommentDTO commentToCommentDTO(Comment comment);
    Comment commentDTOToComment(CommentDTO dto);

}
