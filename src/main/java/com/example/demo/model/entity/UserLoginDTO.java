package com.example.demo.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO {

    @NotBlank(message = "email is required")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @JsonProperty(value = "password_hash")
    private String password;


    @JsonProperty("role")
    private String role;
}