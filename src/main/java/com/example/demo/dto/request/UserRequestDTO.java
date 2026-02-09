package com.example.demo.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for creating and updating User entities.
 * Includes validation rules for all fields.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {
    
    @NotBlank(message = "Username is mandatory")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;
    
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    private String email;
    
    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(regexp = ".*[A-Z].*", message = "Password must contain at least one uppercase letter")
    private String password;
    
    @Size(max = 15, message = "Phone number must not exceed 15 characters")
    @Pattern(regexp = "^[0-9+\\-\\s()]*$", message = "Phone number can only contain digits, +, -, spaces, and parentheses")
    private String phoneNumber;
    
    @Pattern(regexp = "CUSTOMER|ADMIN|SELLER", message = "Role must be CUSTOMER, ADMIN, or SELLER")
    private String role;
}
