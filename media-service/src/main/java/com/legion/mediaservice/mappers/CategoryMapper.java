package com.legion.mediaservice.mappers;

import com.legion.mediaservice.entities.category.Category;
import com.legion.mediaservice.dto.CategoryDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category CategoryDtoToCategoryMapper(CategoryDTO dto);

    CategoryDTO CategoryToCategoryDtoMapper(Category category);
}
