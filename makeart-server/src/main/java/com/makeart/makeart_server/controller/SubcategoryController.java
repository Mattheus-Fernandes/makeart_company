package com.makeart.makeart_server.controller;

import com.makeart.makeart_server.business.SubcategoryService;
import com.makeart.makeart_server.infrastructure.entity.Subcategory;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/subcategory")
@RequiredArgsConstructor
public class SubcategoryController {

    private final SubcategoryService subcategoryService;

    @PostMapping
    public ResponseEntity<Subcategory> saveSubcategory(@RequestBody Subcategory subcategory) {
        return ResponseEntity.ok(subcategoryService.registerSubcategory(subcategory));
    }
}
