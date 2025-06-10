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

    public BrandDTO registerBrand(BrandDTO brandDTO) {
        try {
            codeExist(brandDTO);
            descriptionExist(brandDTO);

            codeExist(brandDTO);

            Brand brand = brandConverter.toBrandEntity(brandDTO);

            return brandConverter.toBrandDTO(brandRepository.save(brand));
        } catch (ConflictException e) {
            throw new ConflictException(e.getMessage());
        }
    }

    public void codeExist(BrandDTO brandDTO) {
        try {
            boolean exist = codeAlreadyExists(brandDTO);

            if (exist) {
                throw new ConflictException("Código já cadastrado " + brandDTO.getCode());
            }
        } catch (ConflictException e) {
            throw new ConflictException(e.getMessage());
        }
    }

    public void descriptionExist(BrandDTO brandDTO) {
        try {
            boolean exist = descriptionAlreadyExists(brandDTO);

            if (exist) {
                throw new ConflictException("Descrição de marca já cadastrada " + brandDTO.getDescription());
            }
        } catch (ConflictException e) {
            throw new ConflictException(e.getMessage());
        }
    }

    public boolean codeAlreadyExists(BrandDTO brandDTO) {
        return brandRepository.existsByCode(brandDTO.getCode());
    }

    public boolean descriptionAlreadyExists(BrandDTO brandDTO) {
        return brandRepository.existsByDescription(brandDTO.getDescription());
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
