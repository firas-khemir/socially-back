package com.legion.mediaservice.mappers;

import com.legion.mediaservice.entities.Tag;
import com.legion.mediaservice.dto.TagDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TagMapper {
    TagMapper INSTANCE = Mappers.getMapper(TagMapper.class);

    TagDto tagTotagDTO(Tag tag);

    Tag tagDtoTotag(TagDto tagDto);

    List<TagDto> tagsToTagDTOs(List<Tag> tags);

    List<Tag> tagDTOsToTags(List<TagDto> tagDTOs );
}
