package com.makeart.makeart_server.controller;

import com.makeart.makeart_server.business.SubcategoryService;
import com.makeart.makeart_server.business.dto.SubcategoryDTO;
import com.makeart.makeart_server.infrastructure.entity.Subcategory;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subcategory")
@RequiredArgsConstructor
public class SubcategoryController {

    private final SubcategoryService subcategoryService;

    @PostMapping
    public ResponseEntity<Subcategory> saveSubcategory(@RequestBody Subcategory subcategory) {
        return ResponseEntity.ok(subcategoryService.registerSubcategory(subcategory));
    }

    @GetMapping("/{code}")
    public ResponseEntity<SubcategoryDTO> findSubcategoryByCode(@PathVariable("code") String code) {
        return ResponseEntity.ok(subcategoryService.filterSubcategoryByCode(code));
    }

    @GetMapping
    public ResponseEntity<List<SubcategoryDTO>> findAllSubcategories() {
        return ResponseEntity.ok(subcategoryService.filterAllSubcategories());
    }

}
