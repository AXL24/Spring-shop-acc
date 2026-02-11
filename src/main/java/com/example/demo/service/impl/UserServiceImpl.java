package com.example.demo.service.impl;

import com.example.demo.dto.request.UserRequestDTO;
import com.example.demo.dto.response.UserResponseDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.entity.User;
import com.example.demo.repository.mysql.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

/**
 * Implementation of UserService.
 * Handles business logic and DTO mapping for user operations.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private org.modelmapper.ModelMapper modelMapper;

    @Override
    @Transactional
    public UserResponseDTO createUser(UserRequestDTO dto) {
        User user = modelMapper.map(dto, User.class);
        // In production, password should be hashed using BCrypt or similar
        user.setPasswordHash(dto.getPassword()); 
        user.setRole(dto.getRole() != null ? dto.getRole() : "CUSTOMER");
        user.setActive(true);
        user.setCreated(Instant.now());
        user.setUpdated(Instant.now());
        
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserResponseDTO.class);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    @Override
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public UserResponseDTO updateUser(Long id, UserRequestDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        
        // Map DTO to existing entity
        modelMapper.map(dto, user);
        
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            // In production, password should be hashed
            user.setPasswordHash(dto.getPassword());
        }
        
        user.setUpdated(Instant.now());
        
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserResponseDTO.class);
    }

    @Override
    @Transactional
    public void softDeleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        user.setActive(false);
        user.setUpdated(Instant.now());
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        userRepository.delete(user);
    }
}
