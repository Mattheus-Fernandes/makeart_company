package com.makeart.makeart_server.controller;

import com.makeart.makeart_server.business.BrandService;
import com.makeart.makeart_server.business.dto.BrandDTO;
import com.makeart.makeart_server.infrastructure.entity.Brand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brand")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    @PostMapping
    public ResponseEntity<Brand> saveBrand(@RequestBody Brand brand) {
        return ResponseEntity.ok(brandService.registerBrand(brand));
    }

    @GetMapping
    public ResponseEntity<List<BrandDTO>> searchBrands(
            @RequestParam(required = false) String code,
            @RequestParam(required=false) String description
    ) {
        if (code == null && description == null) {
            return ResponseEntity.ok(brandService.filterAllBrands());
        }

        return ResponseEntity.ok(brandService.filterBrandByCode(code, description));
    }
}
