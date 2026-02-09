package com.example.demo.service.impl;

import com.example.demo.dto.request.ProductRequestDTO;
import com.example.demo.dto.response.ProductResponseDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.entity.Category;
import com.example.demo.model.entity.Product;
import com.example.demo.repository.mysql.CategoryRepository;
import com.example.demo.repository.mysql.ProductRepository;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

/**
 * Implementation of ProductService.
 * Handles business logic and DTO mapping for product operations.
 */
@Service
public class ProductServiceImpl implements ProductService {
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    @Transactional
    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {
        Category existingCategory = categoryRepository.findById(productRequestDTO.getCategory())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + productRequestDTO.getCategory()));

        Product newProduct = Product.builder()
                .name(productRequestDTO.getName())
                .price(productRequestDTO.getPrice())
                .category(existingCategory)
                .description(productRequestDTO.getDescription())
                .platform(productRequestDTO.getPlatform())
                .stock(productRequestDTO.getStock() != null ? productRequestDTO.getStock() : 0)
                .active(true)
                .created(Instant.now())
                .updated(Instant.now())
                .build();
                
        Product savedProduct = productRepository.save(newProduct);
        return mapToResponseDTO(savedProduct);
    }

    @Override
    public ProductResponseDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return mapToResponseDTO(product);
    }

    @Override
    public Page<ProductResponseDTO> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(this::mapToResponseDTO);
    }

    @Override
    @Transactional
    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        
        Category category = categoryRepository.findById(dto.getCategory())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + dto.getCategory()));
        
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setCategory(category);
        product.setDescription(dto.getDescription());
        product.setPlatform(dto.getPlatform());
        product.setStock(dto.getStock() != null ? dto.getStock() : 0);
        product.setUpdated(Instant.now());
        
        Product updatedProduct = productRepository.save(product);
        return mapToResponseDTO(updatedProduct);
    }

    @Override
    @Transactional
    public void softDeleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        product.setActive(false);
        product.setUpdated(Instant.now());
        productRepository.save(product);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        productRepository.delete(product);
    }

    /**
     * Maps Product entity to ProductResponseDTO.
     * 
     * @param product the product entity
     * @return the product response DTO
     */
    private ProductResponseDTO mapToResponseDTO(Product product) {
        return ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .categoryId(product.getCategory().getId())
                .categoryName(product.getCategory().getName())
                .description(product.getDescription())
                .platform(product.getPlatform())
                .stock(product.getStock())
                .active(product.getActive())
                .created(product.getCreated())
                .updated(product.getUpdated())
                .build();
    }
}
