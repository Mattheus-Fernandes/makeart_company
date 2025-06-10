package com.makeart.makeart_server.business;

import com.makeart.makeart_server.business.converter.CategoryConverter;
import com.makeart.makeart_server.business.dto.CategoryDTO;
import com.makeart.makeart_server.infrastructure.entity.Category;
import com.makeart.makeart_server.infrastructure.exceptions.ConflictException;
import com.makeart.makeart_server.infrastructure.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryConverter categoryConverter;

    public CategoryDTO registerCategory(CategoryDTO categoryDTO) {
        try {
            codeExist(categoryDTO);
            descriptionExist(categoryDTO);

            Category category = categoryConverter.toCategoryEntity(categoryDTO);

            return categoryConverter.toCategoryDTO(categoryRepository.save(category));
        } catch (ConflictException e) {
            throw new ConflictException(e.getMessage());
        }
    }

    public void codeExist(CategoryDTO categoryDTO) {
        try {
            boolean exist = codeAlreadyExists(categoryDTO);

            if (exist) {
                throw new ConflictException("Código para a categoria já cadastrado " + categoryDTO.getCode());
            }
        } catch (ConflictException e) {
            throw new ConflictException(e.getMessage());
        }
    }

    public void descriptionExist(CategoryDTO categoryDTO) {
        try {
            boolean exist = descriptionAlreadyExists(categoryDTO);

            if (exist) {
                throw new ConflictException("Categoria já cadastrada " + categoryDTO.getDescription());
            }
        } catch (ConflictException e) {
            throw new ConflictException(e.getMessage());
        }
    }

    public boolean codeAlreadyExists(CategoryDTO categoryDTO) {
        return categoryRepository.existsByCode(categoryDTO.getCode());
    }

    public boolean descriptionAlreadyExists(CategoryDTO categoryDTO) {
        return categoryRepository.existsByDescription(categoryDTO.getDescription());
    }

    public List<CategoryDTO> filterCategory(String code, String description) {
        try {
            if ((code == null || code.trim().isEmpty()) && (description == null || description.trim().isEmpty())) {
                throw new ConflictException("O código ou nome para pesquisa não pode ser vazio.");
            }

            List<Category> categories = new ArrayList<>();

            if (code != null && !code.trim().isEmpty()) {
                categories = categoryRepository.findByCodeContainsIgnoreCase(code);
            }

            if (description != null && !description.trim().isEmpty()) {
                categories = categoryRepository.findByDescriptionContainsIgnoreCase(description);
            }

            if (categories.isEmpty()) {
                throw new ConflictException("Nenhuma categoria encontrada.");
            }

            return categoryConverter.toListCategoryDTO(categories);
        } catch (ConflictException e) {
            throw new ConflictException(e.getMessage());
        }
    }

    public List<CategoryDTO> filterAllCategories() {
        List<Category> categories = categoryRepository.findAll();

        return categoryConverter.toListCategoryDTO(categories);
    }

    public CategoryDTO updateCategory(String code, CategoryDTO categoryDTO) {
        Category categoryEntity = categoryRepository.findByCode(code).orElseThrow(
                () -> new ConflictException("Categoria não encontrada " + code)
        );

        Category category = categoryConverter.updateCategory(categoryDTO, categoryEntity);

        return categoryConverter.toCategoryDTO(categoryRepository.save(category));

    }
}
