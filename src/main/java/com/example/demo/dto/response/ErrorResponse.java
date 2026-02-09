package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Standard error response DTO for consistent error handling across all API endpoints.
 * This DTO is returned by the GlobalExceptionHandler for all exceptions.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    
    /**
     * HTTP status code (e.g., 404, 400, 500)
     */
    private int status;
    
    /**
     * Error message describing what went wrong
     */
    private String message;
    
    /**
     * Timestamp when the error occurred
     */
    private Instant timestamp;
    
    /**
     * The path/endpoint where the error occurred
     */
    private String path;
}
