package com.legion.mediaservice.mappers;


import com.legion.mediaservice.entities.image.PostImage;
import com.legion.mediaservice.dto.ImageDTO;
import org.mapstruct.Mapper;

@Mapper(uses = { BaseImageMapper.class })
public interface PostImageMapper {

    ImageDTO imageToImageDTO(PostImage postImage);

    PostImage imageDtoToImage(ImageDTO image);

}
