package com.legion.mediaservice.dto;

import com.legion.mediaservice.entities.category.CategoryType;
import com.legion.mediaservice.entities.category.SpecificCategoryType;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CategoryDTO {

    private Long id;

    private Long version;

    private CategoryType type;

    private SpecificCategoryType specificType;

}
