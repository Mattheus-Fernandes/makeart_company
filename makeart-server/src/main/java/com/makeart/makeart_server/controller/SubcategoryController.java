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
    public ResponseEntity<SubcategoryDTO> saveSubcategory(@RequestBody SubcategoryDTO subcategoryDTO) {
        return ResponseEntity.ok(subcategoryService.registerSubcategory(subcategoryDTO));
    }

    @GetMapping
    public ResponseEntity<List<SubcategoryDTO>> findAllSubcategories(
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String categoryCode
    ) {
        if (code == null && description == null && categoryCode == null) {
            return ResponseEntity.ok(subcategoryService.filterAllSubcategories());
        }

        return ResponseEntity.ok(subcategoryService.filterSubcategories(code, description, categoryCode));
    }

    @PutMapping
    public ResponseEntity<SubcategoryDTO> updateSubcategory(
            @RequestParam(required = false) String code,
            @RequestBody SubcategoryDTO subcategoryDTO
    ){
        return ResponseEntity.ok(subcategoryService.updateSubcategory(code, subcategoryDTO));
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Void> deleteSubcategory(@PathVariable String code) {
        subcategoryService.deleteSubcategory(code);
        return ResponseEntity.ok().build();
    }

}
