package com.makeart.makeart_server.business.converter;

import com.makeart.makeart_server.business.dto.ProductDTO;
import com.makeart.makeart_server.infrastructure.entity.Brand;
import com.makeart.makeart_server.infrastructure.entity.Category;
import com.makeart.makeart_server.infrastructure.entity.Product;
import com.makeart.makeart_server.infrastructure.entity.Subcategory;
import com.makeart.makeart_server.infrastructure.exceptions.ConflictException;
import com.makeart.makeart_server.infrastructure.repository.BrandRepository;
import com.makeart.makeart_server.infrastructure.repository.CategoryRepository;
import com.makeart.makeart_server.infrastructure.repository.SubcategoryRepository;
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
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final SubcategoryRepository subcategoryRepository;

    public List<ProductDTO> toProductDTOList(List<Product> productsDTO) {
        List<ProductDTO> products = new ArrayList<ProductDTO>();

        for (Product productDTO : productsDTO) {
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

    public Product updateProduct(ProductDTO productDTO, Product productEntity) {
        Brand brand = productDTO.getBrand() != null ? brandRepository.findByCode(productDTO.getBrand().getCode())
                .orElseThrow(
                        () -> new ConflictException("Marca não encontrada " + productDTO.getBrand().getCode())
                ) : productEntity.getBrand();

        Category category = productDTO.getCategory() != null ? categoryRepository.findByCode(productDTO.getCategory().getCode())
                .orElseThrow(
                        () -> new ConflictException("Categoria não encontrada " + productDTO.getCategory().getCode())
                ) : productEntity.getCategory();

        Subcategory subcategory = productDTO.getSubcategory() != null ? subcategoryRepository.findByCode(productDTO.getSubcategory().getCode())
                .orElseThrow(
                        () -> new ConflictException("Subcategoria não encontrada " + productDTO.getSubcategory().getCode())
                ) : productEntity.getSubcategory();

        return Product.builder()
                .id(productEntity.getId())
                .code(productDTO.getCode() != null ? productDTO.getCode() : productEntity.getCode())
                .description(productDTO.getDescription() != null ? productDTO.getDescription() : productEntity.getDescription())
                .brand(brand)
                .category(category)
                .subcategory(subcategory)
                .imgPath(productDTO.getImgPath() != null ? productDTO.getImgPath() : productEntity.getImgPath())
                .stock(productDTO.getStock() != null ? productDTO.getStock() : productEntity.getStock())
                .costPrice(productDTO.getCostPrice() != null ? productDTO.getCostPrice() : productEntity.getCostPrice())
                .salePrice(productDTO.getSalePrice() != null ? productDTO.getSalePrice() : productEntity.getSalePrice())
                .details(productDTO.getDetails() != null ? productDTO.getDetails() : productEntity.getDetails())
                .build();
    }
}
