package com.makeart.makeart_server.controller;

import com.makeart.makeart_server.business.BrandService;
import com.makeart.makeart_server.infrastructure.entity.Brand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/brand")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    @PostMapping
    public ResponseEntity<Brand> saveBrand(@RequestBody Brand brand) {
        return ResponseEntity.ok(brandService.registerBrand(brand));
    }
}
