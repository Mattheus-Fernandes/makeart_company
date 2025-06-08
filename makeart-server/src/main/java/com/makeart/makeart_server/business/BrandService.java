package com.makeart.makeart_server.business;

import com.makeart.makeart_server.business.converter.BrandConverter;
import com.makeart.makeart_server.business.dto.BrandDTO;
import com.makeart.makeart_server.infrastructure.entity.Brand;
import com.makeart.makeart_server.infrastructure.exceptions.ConflictException;
import com.makeart.makeart_server.infrastructure.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;
    private final BrandConverter brandConverter;

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
            throw new ConflictException(e.getMessage());
        }
    }

    public void descriptionExist(Brand brand) {
        try {
            boolean exist = descriptionAlreadyExists(brand);

            if (exist) {
                throw new ConflictException("Descrição de marca já cadastrada " + brand.getDescription());
            }
        } catch (ConflictException e) {
            throw new ConflictException(e.getMessage());
        }
    }

    public boolean codeAlreadyExists(Brand brand) {
        return brandRepository.existsByCode(brand.getCode());
    }

    public boolean descriptionAlreadyExists(Brand brand) {
        return brandRepository.existsByDescription(brand.getDescription());
    }

    public List<BrandDTO> filterBrandByCode(String code, String description) {
        try {
            if ((code == null || code.trim().isEmpty()) && (description == null || description.trim().isEmpty())) {
                throw new ConflictException("O código ou nome para pesquisa não pode ser vazio.");
            }

            List<Brand> brands = new ArrayList<>();

            if (code != null && !code.trim().isEmpty()) {
                brands = brandRepository.findByCodeContainsIgnoreCase(code);
            }

            if (description != null && !description.trim().isEmpty()) {
                brands = brandRepository.findByDescriptionContainsIgnoreCase(description);
            }

            if (brands.isEmpty()) {
                throw new ConflictException("Nenhuma marca encontrada.");
            }

            return brandConverter.toListBrandDTO(brands);

        } catch (ConflictException e) {
            throw new ConflictException(e.getMessage());
        }
    }

    public List<BrandDTO> filterAllBrands() {
        try {
            List<Brand> brands = brandRepository.findAll();
            return brandConverter.toListBrandDTO(brands);

        } catch (ConflictException e) {
            throw new ConflictException(e.getMessage());
        }
    }

    public BrandDTO updateBrand(String code, BrandDTO brandDTO) {
        Brand brandEntity = brandRepository.findByCode(code).orElseThrow(
                () -> new ConflictException("Marca não encontada " + code)
        );

        Brand brand = brandConverter.updateBrand(brandDTO, brandEntity);

        return brandConverter.toBrandDTO(brandRepository.save(brand));
    }
}
