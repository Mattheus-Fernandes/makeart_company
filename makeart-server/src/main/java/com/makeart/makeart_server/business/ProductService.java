package com.makeart.makeart_server.business;

import com.makeart.makeart_server.business.converter.BrandConverter;
import com.makeart.makeart_server.business.converter.CategoryConverter;
import com.makeart.makeart_server.business.converter.ProductConverter;
import com.makeart.makeart_server.business.converter.SubcategoryConverter;
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
    private final BrandConverter brandConverter;
    private final CategoryConverter categoryConverter;
    private final SubcategoryConverter subcategoryConverter;

    public ProductDTO registerProduct(ProductDTO productDTO) {
        try {
            codeExists(productDTO);
            descriptionExists(productDTO);
            stockIsEmpty(productDTO);

            Brand brand = brandRepository.findByCode(productDTO.getBrand().getCode())
                    .orElseThrow(() -> new ConflictException("Marca não encontrada"));

            productDTO.setBrand(brandConverter.toBrandDTO(brand));

            Category category = categoryRepository.findByCode(productDTO.getCategory().getCode())
                    .orElseThrow(() -> new ConflictException("Categoria não encontrada"));

            productDTO.setCategory(categoryConverter.toCategoryDTO(category));

            Subcategory subcategory = subcategoryRepository.findByCode(productDTO.getSubcategory().getCode())
                    .orElseThrow(() -> new ConflictException("Subcategoria não encontrada"));

            productDTO.setSubcategory(subcategoryConverter.toSubcategorySimpleDTO(subcategory));

            Product product = productConverter.toProductEntity(productDTO);

            return productConverter.toProductDTO(productRepository.save(product));

        } catch (ConflictException e) {
            throw new ConflictException(e.getMessage());
        }
    }

    public void codeExists(ProductDTO productDTO) {
        try {
            boolean exist = codeAlreadyExists(productDTO);

            if (exist) {
                throw new ConflictException("Código para o produto já cadastrado " + productDTO.getCode());
            }
        } catch (ConflictException e) {
            throw new ConflictException(e.getMessage());
        }
    }

    public void descriptionExists(ProductDTO productDTO) {
        try {
            boolean exist = descriptionAlreadyExists(productDTO);

            if (exist) {
                throw new ConflictException("Produto já cadastrado " + productDTO.getDescription());
            }
        } catch (ConflictException e) {
            throw new ConflictException(e.getMessage());
        }
    }

    public void stockIsEmpty(ProductDTO productDTO) {
        try {

            if (productDTO.getStock() <= 0) {
                throw new ConflictException("O valor de estoque precisa ser maior que zero " + productDTO.getStock());
            }

        } catch (ConflictException e) {
            throw new ConflictException(e.getMessage());
        }
    }

    public boolean codeAlreadyExists(ProductDTO productDTO) {
        return productRepository.existsByCode(productDTO.getCode());
    }

    public boolean descriptionAlreadyExists(ProductDTO productDTO) {
        return productRepository.existsByDescription(productDTO.getDescription());
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

    public void deleteProduct(String code) {
        productRepository.deleteProductByCode(code);
    }

}
