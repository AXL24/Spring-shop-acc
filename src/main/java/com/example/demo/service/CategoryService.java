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

    Category findCategoryById(Long id);
    
    CategoryResponseDTO createCategory(CategoryRequestDTO dto);
    
    /**
     * Get a category by its ID.
     * 
     * @param id the category ID
     * @return the category entity
     * @throws com.example.demo.exception.ResourceNotFoundException if category not found
     */
    Category getCategoryById(Long id);
    
    /**
     * Get all categories with pagination.
     * 
     * @param pageable pagination information
     * @return page of category entities
     */
    Page<Category> getAllCategories(Pageable pageable);
    
    CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO dto);
    
    /**
     * Delete a category by its ID.
     * 
     * @param id the category ID
     * @throws com.example.demo.exception.ResourceNotFoundException if category not found
     */
    void deleteCategory(Long id);
}
