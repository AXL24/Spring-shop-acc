package com.example.demo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for creating and updating Account entities.
 * Accounts represent virtual goods (credentials) to be delivered to customers.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequestDTO {
    
    @NotNull(message = "Product ID is mandatory")
    private Long productId;
    
    @NotBlank(message = "Username is mandatory")
    @Size(max = 500, message = "Username must not exceed 500 characters")
    private String username;
    
    @NotBlank(message = "Password is mandatory")
    @Size(max = 500, message = "Password must not exceed 500 characters")
    private String password;
    
    @Pattern(regexp = "AVAILABLE|SOLD|CONTACT", message = "Status must be AVAILABLE, SOLD, or CONTACT")
    private String status;
}
