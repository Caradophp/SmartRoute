package com.faesa.smartRoute.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginDto(
        @NotBlank(message = "E-mail deve ser informado")
        String email,

        @NotBlank(message = "Senha deve ser informada")
        String senha
) {
}
