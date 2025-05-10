package com.makeart.makeart_server.business.converter;

import com.makeart.makeart_server.business.dto.BrandDTO;
import com.makeart.makeart_server.infrastructure.entity.Brand;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BrandConverter {

    public List<BrandDTO> toListBrandDTO(List<Brand> brandsDTOS) {
        List<BrandDTO> brands = new ArrayList<BrandDTO>();

        for (Brand brandDTO: brandsDTOS) {
            brands.add(toBrandDTO(brandDTO));
        }

        return brands;
    }

    public BrandDTO toBrandDTO(Brand brand) {
        return BrandDTO.builder()
                .code(brand.getCode())
                .description(brand.getDescription())
                .build();
    }

}
