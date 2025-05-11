package com.makeart.makeart_server.business;

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

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final SubcategoryRepository subcategoryRepository;

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

}
