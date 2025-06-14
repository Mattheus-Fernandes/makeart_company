package com.makeart.makeart_server.business.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {
    private String code;
    private String description;
    private BrandDTO brand;
    private CategoryDTO category;
    private SubcategorySimpleDTO subcategory;
    private String imgPath;
    private Long stock;
    private Double costPrice;
    private Double salePrice;
    private String details;
}
