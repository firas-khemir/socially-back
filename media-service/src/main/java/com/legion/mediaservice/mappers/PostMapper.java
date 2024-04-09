package com.legion.mediaservice.mappers;


import com.legion.mediaservice.entities.Post;
import com.legion.mediaservice.dto.PostDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    @Mapping(source = "images", target = "images")
    PostDTO postToPostDTO(Post post);

    @Mapping(source = "images", target = "images")
    Post postDtoToPost(PostDTO dto);

}
