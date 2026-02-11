package com.example.demo.service;

import com.example.demo.dto.request.UserRequestDTO;
import com.example.demo.dto.response.UserResponseDTO;
import com.example.demo.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for User entity operations.
 * Provides CRUD operations for users.
 */
public interface UserService {
    
    UserResponseDTO createUser(UserRequestDTO dto);
    
    /**
     * Get a user by ID.
     * 
     * @param id the user ID
     * @return the user entity
     * @throws com.example.demo.exception.ResourceNotFoundException if user not found
     */
    User getUserById(Long id);
    
    /**
     * Get all users with pagination.
     * 
     * @param pageable pagination information
     * @return page of user entities
     */
    Page<User> getAllUsers(Pageable pageable);
    
    UserResponseDTO updateUser(Long id, UserRequestDTO dto);
    
    /**
     * Soft delete a user (set active=false).
     * 
     * @param id the user ID
     * @throws com.example.demo.exception.ResourceNotFoundException if user not found
     */
    void softDeleteUser(Long id);
    
    /**
     * Hard delete a user (remove from database).
     * 
     * @param id the user ID
     * @throws com.example.demo.exception.ResourceNotFoundException if user not found
     */
    void deleteUser(Long id);
}
