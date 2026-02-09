package com.example.demo.service;

import com.example.demo.dto.request.ProductRequestDTO;
import com.example.demo.dto.response.ProductResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for Product entity operations.
 * Provides CRUD operations for products.
 */
public interface ProductService {
    
    /**
     * Create a new product.
     * 
     * @param productRequestDTO the product data
     * @return the created product as ProductResponseDTO
     */
    ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO);
    
    /**
     * Get a product by ID.
     * 
     * @param id the product ID
     * @return the product as ProductResponseDTO
     * @throws com.example.demo.exception.ResourceNotFoundException if product not found
     */
    ProductResponseDTO getProductById(Long id);
    
    /**
     * Get all products with pagination.
     * 
     * @param pageable pagination information
     * @return page of products
     */
    Page<ProductResponseDTO> getAllProducts(Pageable pageable);
    
    /**
     * Update an existing product.
     * 
     * @param id the product ID
     * @param dto the updated product data
     * @return the updated product as ProductResponseDTO
     * @throws com.example.demo.exception.ResourceNotFoundException if product not found
     */
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

