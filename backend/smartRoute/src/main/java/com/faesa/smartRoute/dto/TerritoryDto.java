package com.faesa.smartRoute.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record TerritoryDto(
        @Pattern(regexp = "(?:\\s*|[0-9a-fA-F]{8}-(?:[0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12})",
                message = "Identificador do território inválido")
        String id,

        @NotBlank(message = "Nome do território deve ser informado")
        @Size(max = 60, message = "Nome do território deve ter no máximo 60 caracteres")
        String nome,

        @NotBlank(message = "Tipo do território deve ser informado")
        @Pattern(regexp = "URBAN|RURAL", message = "Tipo de território inválido")
        String type
) {
}
