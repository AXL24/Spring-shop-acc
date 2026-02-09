package com.example.demo.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Response DTO for User entity.
 * Excludes sensitive information like passwordHash.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    
    private Long id;
    private String username;
    private String email;
    @JsonProperty(value = "phone_number")
    private String phoneNumber;
    private String role;
    private Boolean active;
    private Instant created;
    private Instant updated;
}
