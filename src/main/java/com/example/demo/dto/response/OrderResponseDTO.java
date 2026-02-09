package com.example.demo.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty(value = "order_code")
    private String orderCode;
    @JsonProperty(value = "user_id")
    private Long userId;
    private String username;
    @JsonProperty(value = "total_amount")
    private BigDecimal totalAmount;
    private String status;
    @JsonProperty(value = "customer_note")
    private String customerNote;
    @JsonProperty(value = "order_items")
    private List<OrderItemResponseDTO> orderItems;
    private Instant created;
    private Instant updated;
}
