package com.example.demo.service;

import com.example.demo.dto.request.CategoryRequestDTO;
import com.example.demo.dto.response.CategoryResponseDTO;
import com.example.demo.model.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for Category entity operations.
 * Provides CRUD operations for categories.
 */
public interface CategoryService {

    CategoryResponseDTO findCategoryById(Long id);
    
    /**
     * Create a new category.
     * 
     * @param dto the category data
     * @return the created category as CategoryResponseDTO
     */
    CategoryResponseDTO createCategory(CategoryRequestDTO dto);
    
    /**
     * Get a category by its ID.
     * 
     * @param id the category ID
     * @return the category as CategoryResponseDTO
     * @throws com.example.demo.exception.ResourceNotFoundException if category not found
     */
    CategoryResponseDTO getCategoryById(Long id);
    
    /**
     * Get all categories with pagination.
     * 
     * @param pageable pagination information
     * @return page of categories
     */
    Page<CategoryResponseDTO> getAllCategories(Pageable pageable);
    
    /**
     * Update an existing category.
     * 
     * @param id the category ID
     * @param dto the updated category data
     * @return the updated category as CategoryResponseDTO
     * @throws com.example.demo.exception.ResourceNotFoundException if category not found
     */
    CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO dto);
    
    /**
     * Delete a category by its ID.
     * 
     * @param id the category ID
     * @throws com.example.demo.exception.ResourceNotFoundException if category not found
     */
    void deleteCategory(Long id);
}
