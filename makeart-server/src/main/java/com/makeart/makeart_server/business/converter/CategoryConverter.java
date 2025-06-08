package com.makeart.makeart_server.business.converter;

import com.makeart.makeart_server.business.dto.CategoryDTO;
import com.makeart.makeart_server.infrastructure.entity.Category;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryConverter {

    public List<CategoryDTO> toListCategoryDTO(List<Category> categoriesDTO) {
        List<CategoryDTO> categories = new ArrayList<CategoryDTO>();

        for (Category categoryDTO: categoriesDTO) {
            categories.add(toCategoryDTO(categoryDTO));
        }

        return categories;
    }

    public CategoryDTO toCategoryDTO(Category category) {
        return CategoryDTO.builder()
                .code(category.getCode())
                .description(category.getDescription())
                .build();
    }

    public Category updateCategory(CategoryDTO categoryDTO, Category categoryEntity) {
        return Category.builder()
                .id(categoryEntity.getId())
                .code(categoryDTO.getCode() != null ? categoryDTO.getCode() : categoryEntity.getCode())
                .description(categoryDTO.getDescription() != null ? categoryDTO.getDescription() : categoryEntity.getDescription())
                .build();
    }

}
