package com.faesa.smartRoute.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CodeToCheckDto(
        @NotNull(message = "Código deve ser informado")
        Integer codigo,
        @NotBlank(message = "Código deve ser informado")
        @Email(message = "E-mail informado é inválido")
        String email
) {
}
