package com.makeart.makeart_server.controller;

import com.makeart.makeart_server.business.CategoryService;
import com.makeart.makeart_server.business.dto.CategoryDTO;
import com.makeart.makeart_server.infrastructure.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Category> saveCategory(@RequestBody Category category) {
        return ResponseEntity.ok(categoryService.registerCategory(category));
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> searchCategories(
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String description
    ) {
        if (code == null && description == null) {
            return ResponseEntity.ok(categoryService.filterAllCategories());
        }

        return ResponseEntity.ok(categoryService.filterCategory(code, description));
    }
}
