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

    @Autowired
    private org.modelmapper.ModelMapper modelMapper;

    @Override
    @Transactional
    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {
        Category existingCategory = categoryRepository.findById(productRequestDTO.getCategory())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + productRequestDTO.getCategory()));

        Product newProduct = modelMapper.map(productRequestDTO, Product.class);
        newProduct.setCategory(existingCategory);
        newProduct.setStock(productRequestDTO.getStock() != null ? productRequestDTO.getStock() : 0);
        newProduct.setActive(true);
        newProduct.setCreated(Instant.now());
        newProduct.setUpdated(Instant.now());
                
        Product savedProduct = productRepository.save(newProduct);
        return modelMapper.map(savedProduct, ProductResponseDTO.class);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    @Override
    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        
        Category category = categoryRepository.findById(dto.getCategory())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + dto.getCategory()));
        
        // Map DTO to existing entity
        modelMapper.map(dto, product);
        
        product.setCategory(category);
        product.setStock(dto.getStock() != null ? dto.getStock() : 0);
        product.setUpdated(Instant.now());
        
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductResponseDTO.class);
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
}
