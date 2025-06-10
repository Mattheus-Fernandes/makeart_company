package com.makeart.makeart_server.controller;

import com.makeart.makeart_server.business.ProductService;
import com.makeart.makeart_server.business.dto.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDTO> saveProduct(@RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.registerProduct(productDTO));
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> searchProducts(
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String subcategory
    ) {
        if (code == null && description == null && brand == null && category == null && subcategory == null) {
            return ResponseEntity.ok(productService.filterAllProducts());
        }

        return ResponseEntity.ok(productService.filterProducts(code, description, brand, category, subcategory));
    }

    @PutMapping
    public ResponseEntity<ProductDTO> updateProduct(
            @RequestParam(required = false) String code,
            @RequestBody ProductDTO productDTO
    ){
        return ResponseEntity.ok(productService.updateProduct(code, productDTO));
    }
}
