package com.makeart.makeart_server.business.converter;

import com.makeart.makeart_server.business.dto.SubcategoryDTO;
import com.makeart.makeart_server.business.dto.SubcategorySimpleDTO;
import com.makeart.makeart_server.infrastructure.entity.Category;
import com.makeart.makeart_server.infrastructure.entity.Subcategory;
import com.makeart.makeart_server.infrastructure.exceptions.ConflictException;
import com.makeart.makeart_server.infrastructure.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SubcategoryConverter {
    private final CategoryConverter categoryConverter;
    private final CategoryRepository categoryRepository;

    public List<SubcategoryDTO> toSubcategoryDTOList(List<Subcategory> subcategoriesDTO) {
        List<SubcategoryDTO> subcategories = new ArrayList<SubcategoryDTO>();

        for (Subcategory subcategoryDTO : subcategoriesDTO) {
            subcategories.add(toSubcategoryDTO(subcategoryDTO));
        }

        return subcategories;
    }

    public SubcategoryDTO toSubcategoryDTO(Subcategory subcategory) {
        return SubcategoryDTO.builder()
                .code(subcategory.getCode())
                .description(subcategory.getDescription())
                .category(categoryConverter.toCategoryDTO(subcategory.getCategory()))
                .build();
    }

    public SubcategorySimpleDTO toSubcategorySimpleDTO(Subcategory subcategory) {
        return SubcategorySimpleDTO.builder()
                .code(subcategory.getCode())
                .description(subcategory.getDescription())
                .build();
    }

    public Subcategory updateSubcategory(SubcategoryDTO subcategoryDTO, Subcategory subcategoryEntity) {
        Category category = subcategoryDTO.getCategory() != null ? categoryRepository.findByCode(
                subcategoryDTO.getCategory().getCode()).orElseThrow(
                    () -> new ConflictException("Categoria não encontrada")
                )
                : subcategoryEntity.getCategory();

        return Subcategory.builder()
                .id(subcategoryEntity.getId())
                .code(subcategoryDTO.getCode() != null ? subcategoryDTO.getCode() : subcategoryEntity.getCode())
                .description(subcategoryDTO.getDescription() != null ? subcategoryDTO.getDescription() : subcategoryEntity.getDescription())
                .category(category)
                .build();
    }


}
