package com.makeart.makeart_server.business;

import com.makeart.makeart_server.business.converter.BrandConverter;
import com.makeart.makeart_server.business.dto.BrandDTO;
import com.makeart.makeart_server.infrastructure.entity.Brand;
import com.makeart.makeart_server.infrastructure.exceptions.ConflictException;
import com.makeart.makeart_server.infrastructure.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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

    public BrandDTO filterBrandByCode(String code) {
        Brand brand = brandRepository.findByCode(code).orElseThrow(
                () -> new RuntimeException("Marca não encontrada.")
        );

        return new BrandDTO(brand.getCode(), brand.getDescription());
    }

    public List<BrandDTO> filterAllBrands() {
        List<Brand> brands = brandRepository.findAll();

        return brandConverter.toListBrandDTO(brands);
    }
}
