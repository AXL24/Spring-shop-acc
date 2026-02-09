package com.example.demo.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Request DTO for creating orders.
 * Contains user ID, list of order items, and optional customer note.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDTO {
    
    @NotNull(message = "User ID is mandatory")
    private Long userId;
    
    @NotEmpty(message = "Order must contain at least one item")
    @Valid
    private List<OrderItemRequestDTO> orderItems;
    
    @Size(max = 1000, message = "Customer note must not exceed 1000 characters")
    private String customerNote;
}
