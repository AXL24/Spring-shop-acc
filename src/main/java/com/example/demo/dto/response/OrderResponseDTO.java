package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

/**
 * Response DTO for Order entity.
 * Includes user information and nested order items.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {
    
    private Long id;
    private String orderCode;
    private Long userId;
    private String username;
    private BigDecimal totalAmount;
    private String status;
    private String customerNote;
    private List<OrderItemResponseDTO> orderItems;
    private Instant created;
    private Instant updated;
}
