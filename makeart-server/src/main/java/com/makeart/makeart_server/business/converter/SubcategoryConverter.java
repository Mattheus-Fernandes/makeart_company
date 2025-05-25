package com.makeart.makeart_server.business.converter;

import com.makeart.makeart_server.business.dto.SubcategoryDTO;
import com.makeart.makeart_server.business.dto.SubcategorySimpleDTO;
import com.makeart.makeart_server.infrastructure.entity.Subcategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SubcategoryConverter {
    private final CategoryConverter categoryConverter;

    public List<SubcategoryDTO> toSubcategoryDTOList(List<Subcategory> subcategoriesDTO) {
        List<SubcategoryDTO> subcategories = new ArrayList<SubcategoryDTO>();

        for (Subcategory subcategoryDTO: subcategoriesDTO) {
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
}
