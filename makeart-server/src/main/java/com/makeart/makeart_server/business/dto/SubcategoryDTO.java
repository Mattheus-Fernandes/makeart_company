package com.makeart.makeart_server.business.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubcategoryDTO {
    private String code;
    private String description;
    private CategoryDTO category;
}
