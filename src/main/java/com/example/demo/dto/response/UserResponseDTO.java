package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

@Data
@AllArgsConstructor

public class UserResponseDTO {
    @NotNull
    private String name;
}
