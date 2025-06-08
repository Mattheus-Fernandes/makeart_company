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
        try {
            boolean codeEmpty = (code == null || code.trim().isEmpty());
            boolean descriptionEmpty = (description == null || description.trim().isEmpty());
            boolean brandEmpty = (brandCode == null || brandCode.trim().isEmpty());
            boolean categoryEmpty = (categoryCode == null || categoryCode.trim().isEmpty());
            boolean subcategoryEmpty = (subcategoryCode == null || subcategoryCode.trim().isEmpty());
            boolean searchInvalid = codeEmpty && descriptionEmpty && brandEmpty && categoryEmpty && subcategoryEmpty;

            if (searchInvalid) {
                throw new ConflictException("Digite o código, nome, marca, categoria ou subcategoria para buscar o produto.");
            }

            List<Product> products = new ArrayList<Product>();

            if (code != null && !code.trim().isEmpty()) {
                products = productRepository.findByCodeContainsIgnoreCase(code);
            } else if (description != null && !description.trim().isEmpty()) {
                products = productRepository.findByDescriptionContainsIgnoreCase(description);
            } else if (brandCode != null && !brandCode.trim().isEmpty()) {
                products = productRepository.findByBrand_CodeContainsIgnoreCase(brandCode);
            } else if (categoryCode != null && !categoryCode.trim().isEmpty()) {
                products = productRepository.findByCategory_CodeContainsIgnoreCase(categoryCode);
            } else if (subcategoryCode != null && !subcategoryCode.trim().isEmpty()) {
                products = productRepository.findBySubcategory_CodeContainsIgnoreCase(subcategoryCode);
            }

            if (products.isEmpty()) {
                throw new ConflictException("Nenhum produto encontrado.");
            }

            return productConverter.toProductDTOList(products);

        } catch (ConflictException e) {
            throw new ConflictException(e.getMessage());
        }
    }

    public List<ProductDTO> filterAllProducts() {
        List<Product> products = productRepository.findAll();

        isEmpty(products);

        return productConverter.toProductDTOList(products);
    }

    public void isEmpty(List<Product> productsDTO) {
        if (productsDTO == null || productsDTO.isEmpty()) {
            throw new RuntimeException("Nenhum produto encontrado");
        }
    }

    public ProductDTO updateProduct(String code, ProductDTO productDTO) {
        Product productEntity = productRepository.findByCode(code).orElseThrow(
                () -> new ConflictException("Produto não encontado " + code)
        );

        Product product = productConverter.updateProduct(productDTO, productEntity);

        return productConverter.toProductDTO(productRepository.save(product));
    }

}
