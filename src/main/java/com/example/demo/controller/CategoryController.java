package com.example.demo.controller;

import com.example.demo.dto.request.CategoryRequestDTO;
import com.example.demo.dto.response.CategoryResponseDTO;
import com.example.demo.model.entity.Category;
import com.example.demo.service.CategoryService;
import com.example.demo.service.impl.FileStorageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

/**
 * REST controller for Category operations.
 * Provides endpoints for category CRUD operations with image upload support.
 */
@RestController
@Validated
@RequestMapping("/api/v1/category")
public class CategoryController {
    
    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private org.modelmapper.ModelMapper modelMapper;

    /**
     * Create a new category with optional image upload.
     * 
     * @param dto category data
     * @param image optional category image
     * @return the created category
     */
    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CategoryResponseDTO> addCategory(
            @Valid @RequestPart("category") CategoryRequestDTO dto,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        try {
            // 1. Upload image if provided
            String imageUrl = null;
            if (image != null && !image.isEmpty()) {
                imageUrl = fileStorageService.uploadFile(image);
            }

            // 2. Set image URL in DTO
            dto.setImageUrl(imageUrl);

            // 3. Create category
            CategoryResponseDTO response = categoryService.createCategory(dto);

            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (IllegalArgumentException e) {
            // Validation error (file size, type, etc.)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());

        } catch (IOException e) {
            // File upload error
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to upload image: " + e.getMessage()
            );
        }
    }

    /**
     * Get a category by ID.
     * 
     * @param id the category ID
     * @return the category
     */
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> getCategoryById(@PathVariable("id") Long id) {
        Category category = categoryService.getCategoryById(id);
        CategoryResponseDTO response = modelMapper.map(category, CategoryResponseDTO.class);
        return ResponseEntity.ok(response);
    }

    /**
     * Get all categories with pagination.
     * 
     * @param page page number (default: 0)
     * @param size page size (default: 10)
     * @return page of categories
     */
    @GetMapping("/getAll")
    public ResponseEntity<Page<CategoryResponseDTO>> getAllCategories(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Category> categories = categoryService.getAllCategories(pageable);
        Page<CategoryResponseDTO> response = categories.map(category -> modelMapper.map(category, CategoryResponseDTO.class));
        return ResponseEntity.ok(response);
    }

    /**
     * Update a category with optional image replacement.
     * 
     * @param id the category ID
     * @param dto updated category data
     * @param image optional new category image
     * @return the updated category
     */
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CategoryResponseDTO> updateCategory(
            @PathVariable("id") Long id,
            @Valid @RequestPart("category") CategoryRequestDTO dto,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        try {
            // 1. Get existing category
            Category existingCategory = categoryService.findCategoryById(id);

            // 2. Handle image update
            if (image != null && !image.isEmpty()) {
                // Delete old image if exists
                if (existingCategory.getImageUrl() != null) {
                    fileStorageService.deleteFile(existingCategory.getImageUrl());
                }

                // Upload new image
                String newImageUrl = fileStorageService.uploadFile(image);
                dto.setImageUrl(newImageUrl);
            } else {
                // Keep existing image URL
                dto.setImageUrl(existingCategory.getImageUrl());
            }

            // 3. Update category
            CategoryResponseDTO response = categoryService.updateCategory(id, dto);

            return ResponseEntity.ok(response);
        } catch (IOException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to upload image: " + e.getMessage()
            );
        }
    }

    /**
     * Delete a category and its associated image.
     * 
     * @param id the category ID
     * @return no content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("id") Long id) {
        // Get category to delete associated image
        Category category = categoryService.findCategoryById(id);
        
        // Delete image if exists
        if (category.getImageUrl() != null) {
            try {
                fileStorageService.deleteFile(category.getImageUrl());
            } catch (Exception e) {
                // Log error but continue with category deletion
                System.err.println("Failed to delete image: " + e.getMessage());
            }
        }
        
        // Delete category
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
