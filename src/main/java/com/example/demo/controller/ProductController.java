package com.example.demo.controller;

import com.example.demo.dto.request.ProductRequestDTO;
import com.example.demo.dto.response.ProductResponseDTO;
import com.example.demo.model.entity.Product;
import com.example.demo.service.ProductService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for Product operations.
 * Provides endpoints for product CRUD operations.
 */
@RestController
@Validated
@RequestMapping("api/v1/product")
public class ProductController {
    
    @Autowired
    private ProductService productService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Create a new product.
     * 
     * @param dto product data
     * @return the created product
     */
    @PostMapping("/add")
    public ResponseEntity<ProductResponseDTO> addProduct(@Valid @RequestBody ProductRequestDTO dto) {
        ProductResponseDTO response = productService.createProduct(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Get a product by ID.
     * 
     * @param id the product ID
     * @return the product
     */
    @GetMapping("/findById/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable("id") Long id) {
        Product product = productService.getProductById(id);
        ProductResponseDTO response = modelMapper.map(product, ProductResponseDTO.class);
        return ResponseEntity.ok(response);
    }

    /**
     * Get all products with pagination.
     * 
     * @param page page number (default: 0)
     * @param size page size (default: 10)
     * @return page of products
     */
    @GetMapping("/getAll")
    public ResponseEntity<Page<ProductResponseDTO>> getAllProducts(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = productService.getAllProducts(pageable);
        Page<ProductResponseDTO> response = products.map(product -> modelMapper.map(product, ProductResponseDTO.class));
        return ResponseEntity.ok(response);
    }

    /**
     * Update a product.
     * 
     * @param id the product ID
     * @param dto updated product data
     * @return the updated product
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(
            @PathVariable("id") Long id,
            @Valid @RequestBody ProductRequestDTO dto) {
        ProductResponseDTO response = productService.updateProduct(id, dto);
        return ResponseEntity.ok(response);
    }

    /**
     * Soft delete a product (set active=false).
     * 
     * @param id the product ID
     * @return no content
     */
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateProduct(@PathVariable("id") Long id) {
        productService.softDeleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Hard delete a product.
     * 
     * @param id the product ID
     * @return no content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
