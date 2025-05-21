package com.makeart.makeart_server.business;

import com.makeart.makeart_server.business.converter.CategoryConverter;
import com.makeart.makeart_server.business.converter.SubcategoryConverter;
import com.makeart.makeart_server.business.dto.SubcategoryDTO;
import com.makeart.makeart_server.infrastructure.entity.Category;
import com.makeart.makeart_server.infrastructure.entity.Subcategory;
import com.makeart.makeart_server.infrastructure.exceptions.ConflictException;
import com.makeart.makeart_server.infrastructure.repository.CategoryRepository;
import com.makeart.makeart_server.infrastructure.repository.SubcategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class SubcategoryService {

    private final SubcategoryRepository subcategoryRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryConverter categoryConverter;
    public final SubcategoryConverter subcategoryConverter;


    public Subcategory registerSubcategory(Subcategory subcategory) {
        try {
            codeExist(subcategory);
            descriptionExist(subcategory);

            Category category = categoryRepository.findByCode(subcategory.getCategory().getCode())
                    .orElseThrow(() -> new ConflictException("Categoria não encontrada"));

            subcategory.setCategory(category);

            return subcategoryRepository.save(subcategory);
        } catch (ConflictException e) {
            throw new ConflictException(e.getMessage());
        }
    }

    public void codeExist(Subcategory subcategory) {
        try {
            boolean exist = codeAlreadyExists(subcategory);

            if (exist) {
                throw new ConflictException("Código para subcategoria já cadastrado " + subcategory.getCode());
            }
        } catch (ConflictException e) {
            throw new ConflictException(e.getMessage());
        }
    }

    public void descriptionExist(Subcategory subcategory) {
        try {
            boolean exist = descriptionAlreadyExists(subcategory);

            if (exist) {
                throw new ConflictException("Subcategoria já cadastrada " + subcategory.getDescription());
            }
        } catch (ConflictException e) {
            throw new ConflictException(e.getMessage());
        }
    }

    public boolean codeAlreadyExists(Subcategory subcategory) {
        return subcategoryRepository.existsByCode(subcategory.getCode());
    }

    public boolean descriptionAlreadyExists(Subcategory subcategory) {
        return subcategoryRepository.existsByDescription(subcategory.getDescription());
    }

    public SubcategoryDTO filterSubcategoryByCode(String code) {
        Subcategory subcategory = subcategoryRepository.findByCode(code).orElseThrow(
                () -> new RuntimeException("Nenhuma subcategoria encontrada.")
        );

        return SubcategoryDTO.builder()
                .code(subcategory.getCode())
                .description(subcategory.getDescription())
                .category(categoryConverter.toCategoryDTO(subcategory.getCategory()))
                .build();
    }

    public List<SubcategoryDTO> filterAllSubcategories() {
        List<Subcategory> subcategories = subcategoryRepository.findAll();

        return subcategoryConverter.toSubcategoryDTOList(subcategories);
    }
}
