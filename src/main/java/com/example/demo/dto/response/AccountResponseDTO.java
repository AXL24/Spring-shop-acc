package com.example.demo.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Response DTO for Account entity (virtual goods).
 * Includes product information for convenience.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponseDTO {
    
    private Long id;
    @JsonProperty(value = "product_id")
    private Long productId;
    @JsonProperty(value = "product_name")
    private String productName;
    private String username;
    private String password;
    private String status;
    private Instant sold;
    private Instant created;
}
