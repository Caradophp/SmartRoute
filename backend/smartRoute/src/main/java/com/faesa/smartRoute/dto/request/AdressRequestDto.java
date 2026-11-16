package com.faesa.smartRoute.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record AdressRequestDto(
        @Pattern(regexp = "(?:\\s*|[0-9a-fA-F]{8}-(?:[0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12})",
                message = "Identificador do endereço inválido")
        String id,

        @NotBlank(message = "Rua deve ser informada")
        @Size(max = 50, message = "Rua deve ter no máximo 50 caracteres")
        String rua,

        @NotNull(message = "Número do endereço deve ser informado")
        @Positive(message = "Número do endereço deve ser positivo")
        Integer numero,

        @NotBlank(message = "Bairro deve ser informado")
        @Size(max = 50, message = "Bairro deve ter no máximo 50 caracteres")
        String bairro,

        @NotBlank(message = "Cidade deve ser informada")
        @Size(max = 50, message = "Cidade deve ter no máximo 50 caracteres")
        String cidade,

        @NotBlank(message = "Território deve ser informado")
        @Pattern(regexp = "[0-9a-fA-F]{8}-(?:[0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}",
                message = "Identificador do território inválido")
        String territoryId
) {
}
