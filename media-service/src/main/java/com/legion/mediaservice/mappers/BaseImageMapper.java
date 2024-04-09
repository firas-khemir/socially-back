package com.legion.mediaservice.mappers;


import com.legion.mediaservice.entities.image.BaseImage;
import com.legion.mediaservice.dto.ImageDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BaseImageMapper {
    BaseImageMapper INSTANCE = Mappers.getMapper(BaseImageMapper.class);

    @Mapping(source = "tags", target = "tags")
    ImageDTO imageToImageDTO(BaseImage postImage);

    @Mapping(source = "tags", target = "tags")
    BaseImage imageDtoToImage(ImageDTO image);

}
