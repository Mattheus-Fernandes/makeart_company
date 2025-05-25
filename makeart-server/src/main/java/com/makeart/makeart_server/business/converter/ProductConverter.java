package com.makeart.makeart_server.business.converter;

import com.makeart.makeart_server.business.dto.ProductDTO;
import com.makeart.makeart_server.infrastructure.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductConverter {

    private final BrandConverter brandConverter;
    private final CategoryConverter categoryConverter;
    private final SubcategoryConverter subcategoryConverter;

    public List<ProductDTO> toProductDTOList(List<Product> productsDTO) {
        List<ProductDTO> products = new ArrayList<ProductDTO>();

        for (Product productDTO: productsDTO) {
            products.add(toProductDTO(productDTO));
        }

        return products;
     }

    public ProductDTO toProductDTO(Product product) {
        return ProductDTO.builder()
                .code(product.getCode())
                .description(product.getDescription())
                .brand(brandConverter.toBrandDTO(product.getBrand()))
                .category(categoryConverter.toCategoryDTO(product.getCategory()))
                .subcategory(subcategoryConverter.toSubcategorySimpleDTO(product.getSubcategory()))
                .imgPath(product.getImgPath())
                .stock(product.getStock())
                .costPrice(product.getCostPrice())
                .salePrice(product.getSalePrice())
                .details(product.getDetails())
                .build();
    }
}
