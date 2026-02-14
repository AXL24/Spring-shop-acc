package com.example.demo.dto.response;

import com.example.demo.model.entity.Account;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Response DTO for OrderItem entity.
 * Includes product information for convenience.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponseDTO {
    
    private Long id;
    @JsonProperty(value = "product_id")
    private Long productId;
    @JsonProperty(value = "product_name")
    private String productName;
    private Integer quantity;
    @JsonProperty(value = "unit_price")
    private BigDecimal unitPrice;
    @JsonProperty(value = "total_price")
    private BigDecimal totalPrice;

    private java.util.List<AccountResponseDTO> accounts;

    private Instant delivered;
}
