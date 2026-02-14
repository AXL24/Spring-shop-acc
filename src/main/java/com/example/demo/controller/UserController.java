package com.example.demo.controller;

import com.example.demo.dto.request.UserRequestDTO;
import com.example.demo.dto.response.UserResponseDTO;
import com.example.demo.model.entity.User;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for User operations.
 * Provides endpoints for user CRUD operations.
 */
@RestController
@RequestMapping("api/v1/user")
public class UserController {
    
    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Create a new user.
     * 
     * @param dto user data
     * @return the created user
     */
    @PostMapping("/add")
    public ResponseEntity<UserResponseDTO> addUser(@Valid @RequestBody UserRequestDTO dto) {
        UserResponseDTO response = userService.createUser(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Get a user by ID.
     * 
     * @param id the user ID
     * @return the user
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable("id") Long id) {
        User user = userService.getUserById(id);
        UserResponseDTO response = modelMapper.map(user, UserResponseDTO.class);
        return ResponseEntity.ok(response);
    }

    /**
     * Get all users with pagination.
     * 
     * @param page page number (default: 0)
     * @param size page size (default: 10)
     * @return page of users
     */
    @GetMapping("/getAll")
    public ResponseEntity<Page<UserResponseDTO>> getAllUsers(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> users = userService.getAllUsers(pageable);
        Page<UserResponseDTO> response = users.map(user -> modelMapper.map(user, UserResponseDTO.class));
        return ResponseEntity.ok(response);
    }

    /**
     * Update a user.
     * 
     * @param id the user ID
     * @param dto updated user data
     * @return the updated user
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(
            @PathVariable("id") Long id,
            @Valid @RequestBody UserRequestDTO dto) {
        UserResponseDTO response = userService.updateUser(id, dto);
        return ResponseEntity.ok(response);
    }

    /**
     * Soft delete a user (set active=false).
     * 
     * @param id the user ID
     * @return no content
     */
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateUser(@PathVariable("id") Long id) {
        userService.softDeleteUser(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Hard delete a user.
     * 
     * @param id the user ID
     * @return no content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
