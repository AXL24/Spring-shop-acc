package com.example.demo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

/**
 * Request DTO for creating and updating Category entities.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequestDTO {
    
    @NotBlank(message = "Category name is mandatory")
    @Size(min = 2, max = 100, message = "Category name must be between 2 and 100 characters")
    private String name;
    
    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;


    private String imageUrl;
}
