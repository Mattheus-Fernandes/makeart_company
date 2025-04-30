package com.makeart.makeart_server.business;

import com.makeart.makeart_server.infrastructure.entity.Category;
import com.makeart.makeart_server.infrastructure.exceptions.ConflictException;
import com.makeart.makeart_server.infrastructure.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category registerCategory(Category category) {
        try {
            codeExist(category);
            descriptionExist(category);
            return  categoryRepository.save(category);
        } catch (ConflictException e) {
            throw new ConflictException(e.getMessage());
        }
    }

    public void codeExist(Category category) {
        try {
            boolean exist = codeAlreadyExists(category);

            if (exist) {
                throw new ConflictException("Código para a categoria já cadastrado " + category.getCode());
            }
        } catch (ConflictException e) {
            throw new ConflictException("Erro ao verificar código " + e.getMessage());
        }
    }

    public void descriptionExist(Category category) {
        try {
            boolean exist = descriptionAlreadyExists(category);

            if(exist) {
                throw new ConflictException("Categoria já cadastrada " + category.getDescription());
            }
        } catch (ConflictException e) {
            throw new ConflictException("Erro ao verificar categoria " + e.getMessage());
        }
    }

    public boolean codeAlreadyExists(Category category) {
        return categoryRepository.existsByCode(category.getCode());
    }

    public boolean descriptionAlreadyExists(Category category) {
        return categoryRepository.existsByDescription(category.getCode());
    }

}
