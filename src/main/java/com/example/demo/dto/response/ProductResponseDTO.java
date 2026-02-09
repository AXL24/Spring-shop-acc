package com.example.demo.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Response DTO for Product entity.
 * Includes category information for convenience.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDTO {
    
    private Long id;
    private String name;
    private BigDecimal price;
    @JsonProperty(value = "category_id")
    private Long categoryId;
    @JsonProperty(value = "category_name")
    private String categoryName;
    private String description;
    private String platform;
    private Integer stock;
    private Boolean active;
    private Instant created;
    private Instant updated;
}
