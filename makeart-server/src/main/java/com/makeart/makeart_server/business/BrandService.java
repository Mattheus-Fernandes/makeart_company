package com.makeart.makeart_server.business;

import com.makeart.makeart_server.infrastructure.entity.Brand;
import com.makeart.makeart_server.infrastructure.exceptions.ConflictException;
import com.makeart.makeart_server.infrastructure.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;

    public Brand registerBrand(Brand brand) {
        try {
            codeExist(brand);
            descriptionExist(brand);
            return brandRepository.save(brand);
        } catch (ConflictException e) {
            throw new ConflictException(e.getMessage());
        }
    }

    public void codeExist(Brand brand) {
        try {
            boolean exist = codeAlreadyExists(brand);

            if (exist) {
                throw new ConflictException("Código já cadastrado " + brand.getCode());
            }
        } catch (ConflictException e) {
            throw new ConflictException("Erro ao verificar código " + e.getMessage());
        }
    }

    public void descriptionExist(Brand brand) {
        try {
            boolean exist = descriptionAlreadyExists(brand);

            if (exist) {
                throw new ConflictException("Descrição de marca já cadastrada " + brand.getDescription());
            }
        } catch (ConflictException e) {
            throw new ConflictException("Erro ao verificar descrição " + e.getMessage());
        }
    }

    public boolean codeAlreadyExists(Brand brand) {
        return brandRepository.existsByCode(brand.getCode());
    }

    public boolean descriptionAlreadyExists(Brand brand) {
        return brandRepository.existsByDescription(brand.getDescription());
    }
}
