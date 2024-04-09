package com.legion.mediaservice.mappers;


import com.legion.mediaservice.entities.image.EventImage;
import com.legion.mediaservice.dto.ImageDTO;
import org.mapstruct.Mapper;

@Mapper(uses = { BaseImageMapper.class })
public interface EventImageMapper {

    ImageDTO imageToImageDTO(EventImage postImage);

    EventImage imageDtoToImage(ImageDTO image);

}
