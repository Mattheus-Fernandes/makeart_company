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

    @GetMapping("/{code}")
    public ResponseEntity<BrandDTO> findBrandByCode(@PathVariable("code") String code) {
        return ResponseEntity.ok(brandService.filterBrandByCode(code));
    }

    @GetMapping
    public ResponseEntity<List<BrandDTO>> findAllBrand() {
        return ResponseEntity.ok(brandService.filterAllBrands());
    }

}
