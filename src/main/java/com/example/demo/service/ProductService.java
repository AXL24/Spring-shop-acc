package com.example.demo.service;

import com.example.demo.dto.request.ProductRequestDTO;
import com.example.demo.dto.response.ProductResponseDTO;
import com.example.demo.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for Product entity operations.
 * Provides CRUD operations for products.
 */
public interface ProductService {
    
    ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO);
    
    /**
     * Get a product by ID.
     * 
     * @param id the product ID
     * @return the product entity
     * @throws com.example.demo.exception.ResourceNotFoundException if product not found
     */
    Product getProductById(Long id);
    
    /**
     * Get all products with pagination.
     * 
     * @param pageable pagination information
     * @return page of product entities
     */
    Page<Product> getAllProducts(Pageable pageable);
    
    ProductResponseDTO updateProduct(Long id, ProductRequestDTO dto);
    
    /**
     * Soft delete a product (set active=false).
     * 
     * @param id the product ID
     * @throws com.example.demo.exception.ResourceNotFoundException if product not found
     */
    void softDeleteProduct(Long id);
    
    /**
     * Hard delete a product (remove from database).
     * 
     * @param id the product ID
     * @throws com.example.demo.exception.ResourceNotFoundException if product not found
     */
    void deleteProduct(Long id);
}

