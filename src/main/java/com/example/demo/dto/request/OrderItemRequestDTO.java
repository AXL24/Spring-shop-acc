package com.example.demo.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for creating order items.
 * Represents a single product and quantity in an order.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRequestDTO {
    
    @NotNull(message = "Product ID is mandatory")
    @JsonProperty(value = "product_id")
    private Long productId;
    
    @NotNull(message = "Quantity is mandatory")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;
}
