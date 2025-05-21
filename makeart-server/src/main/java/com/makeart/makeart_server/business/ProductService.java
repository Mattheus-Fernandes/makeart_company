package com.makeart.makeart_server.business;

import com.makeart.makeart_server.business.converter.ProductConverter;
import com.makeart.makeart_server.business.dto.ProductDTO;
import com.makeart.makeart_server.infrastructure.entity.Brand;
import com.makeart.makeart_server.infrastructure.entity.Category;
import com.makeart.makeart_server.infrastructure.entity.Product;
import com.makeart.makeart_server.infrastructure.entity.Subcategory;
import com.makeart.makeart_server.infrastructure.exceptions.ConflictException;
import com.makeart.makeart_server.infrastructure.repository.BrandRepository;
import com.makeart.makeart_server.infrastructure.repository.CategoryRepository;
import com.makeart.makeart_server.infrastructure.repository.ProductRepository;
import com.makeart.makeart_server.infrastructure.repository.SubcategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final SubcategoryRepository subcategoryRepository;
    private final ProductConverter productConverter;

    public Product registerProduct(Product product) {
        try {
            codeExists(product);
            descriptionExists(product);
            stockIsEmpty(product);

            Brand brand = brandRepository.findByCode(product.getBrand().getCode())
                    .orElseThrow(() -> new ConflictException("Marca não encontrada"));

            product.setBrand(brand);

            Category category = categoryRepository.findByCode(product.getCategory().getCode())
                    .orElseThrow(() -> new ConflictException("Categoria não encontrada"));

            product.setCategory(category);

            Subcategory subcategory = subcategoryRepository.findByCode(product.getSubcategory().getCode())
                    .orElseThrow(() -> new ConflictException("Subcategoria não encontrada"));

            product.setSubcategory(subcategory);

            return productRepository.save(product);
        } catch (ConflictException e) {
            throw new ConflictException(e.getMessage());
        }
    }

    public void codeExists(Product product) {
        try {
            boolean exist = codeAlreadyExists(product);

            if (exist) {
                throw new ConflictException("Código para o produto já cadastrado " + product.getCode());
            }
        } catch (ConflictException e) {
            throw new ConflictException(e.getMessage());
        }
    }

    public void descriptionExists(Product product) {
        try {
            boolean exist = descriptionAlreadyExists(product);

            if (exist) {
                throw new ConflictException("Produto já cadastrado " + product.getDescription());
            }
        } catch (ConflictException e) {
            throw new ConflictException(e.getMessage());
        }
    }

    public void stockIsEmpty(Product product) {
        try {

            if (product.getStock() <= 0) {
                throw new ConflictException("O valor de estoque precisa ser maior que zero " + product.getStock());
            }

        } catch (ConflictException e) {
            throw new ConflictException(e.getMessage());
        }
    }

    public boolean codeAlreadyExists(Product product) {
        return productRepository.existsByCode(product.getCode());
    }

    public boolean descriptionAlreadyExists(Product product) {
        return productRepository.existsByDescription(product.getDescription());
    }

    public List<ProductDTO> filterProducts(
            String code, String description, String brandCode, String categoryCode, String subcategoryCode
    ) {
        List<Product> products = new ArrayList<Product>();

        if (code != null) {
            Product product = productRepository.findByCode(code).orElseThrow(
                    () -> new RuntimeException("Nenhum produto encontrado.")
            );

            return List.of(productConverter.toProductDTO(product));
        }

        if (brandCode != null) {
            products = productRepository.findAllByBrandCode(brandCode);
            isEmptu(products);
        } else if (description != null) {
            products = productRepository.findByDescriptionStartingWithIgnoreCase(description);
            isEmptu(products);
        } else if (categoryCode != null) {
            products = productRepository.findAllByCategoryCode(categoryCode);
            isEmptu(products);
        } else if (subcategoryCode != null) {
            products = productRepository.findAllBySubcategoryCode(subcategoryCode);
            isEmptu(products);
        } else {
            products = productRepository.findAll();
        }

        return productConverter.toProductDTOList(products);

    }

    public List<ProductDTO> filterAllProducts() {
        List<Product> products = productRepository.findAll();

        isEmptu(products);

        return productConverter.toProductDTOList(products);
    }

    public void isEmptu(List<Product> productsDTO) {
        if (productsDTO == null || productsDTO.isEmpty()) {
            throw new RuntimeException("Nenhum produto encontrado");
        }
    }

}
