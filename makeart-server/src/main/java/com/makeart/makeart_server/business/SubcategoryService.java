package com.makeart.makeart_server.business;

import com.makeart.makeart_server.business.converter.SubcategoryConverter;
import com.makeart.makeart_server.business.dto.SubcategoryDTO;
import com.makeart.makeart_server.infrastructure.entity.Category;
import com.makeart.makeart_server.infrastructure.entity.Subcategory;
import com.makeart.makeart_server.infrastructure.exceptions.ConflictException;
import com.makeart.makeart_server.infrastructure.repository.CategoryRepository;
import com.makeart.makeart_server.infrastructure.repository.SubcategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubcategoryService {

    private final SubcategoryRepository subcategoryRepository;
    private final CategoryRepository categoryRepository;
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

    public List<SubcategoryDTO> filterSubcategories(String code, String description, String categoryCode) {
        try {
            if (
                    (code == null || code.trim().isEmpty()) &&
                    (description == null || description.trim().isEmpty()) &&
                    (categoryCode == null || categoryCode.trim().isEmpty())
            ) {
                throw new ConflictException("O código ou nome para pesquisa não pode ser vazio.");
            }

            List<Subcategory> subcategories = new ArrayList<>();

            if (code != null && !code.trim().isEmpty()) {
                subcategories = subcategoryRepository.findByCodeContainsIgnoreCase(code);
            }

            if (description != null && !description.trim().isEmpty()) {
                subcategories = subcategoryRepository.findByDescriptionContainsIgnoreCase(description);
            }

            if (categoryCode != null && !categoryCode.trim().isEmpty()) {
                subcategories = subcategoryRepository.findByCategory_CodeContainsIgnoreCase(categoryCode);
            }

            if ((code != null && !code.trim().isEmpty()) && (categoryCode != null && !categoryCode.trim().isEmpty())) {
                subcategories = subcategoryRepository.findByCodeContainsIgnoreCaseAndCategory_CodeContainsIgnoreCase(code, categoryCode);
            }

            if (subcategories.isEmpty()) {
                throw new ConflictException("Nenhuma categoria encontrada.");
            }

            return subcategoryConverter.toSubcategoryDTOList(subcategories);
        } catch (ConflictException e) {
            throw new ConflictException(e.getMessage());
        }
    }

    public List<SubcategoryDTO> filterAllSubcategories() {
        List<Subcategory> subcategories = subcategoryRepository.findAll();

        return subcategoryConverter.toSubcategoryDTOList(subcategories);
    }

    public SubcategoryDTO updateSubcategory(String code, SubcategoryDTO subcategoryDTO) {
        Subcategory subcategoryEntity = subcategoryRepository.findByCode(code).orElseThrow(
                () -> new ConflictException("Subcategoria não encontrada " + code)
        );

        Subcategory subcategory = subcategoryConverter.updateSubcategory(subcategoryDTO, subcategoryEntity);

        return subcategoryConverter.toSubcategoryDTO(subcategoryRepository.save(subcategory));
    }
}
