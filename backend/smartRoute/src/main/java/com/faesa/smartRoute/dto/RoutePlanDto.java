package com.faesa.smartRoute.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record RoutePlanDto(
        @Pattern(regexp = "(?:\\s*|[0-9a-fA-F]{8}-(?:[0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12})",
                message = "Identificador do plano de rota inválido")
        String id,

        @NotBlank(message = "Nome do plano de rota deve ser informado")
        @Size(max = 50, message = "Nome do plano de rota deve ter no máximo 50 caracteres")
        String nome,

        @NotNull(message = "Data do plano de rota deve ser informada")
        LocalDate data,

        @NotBlank(message = "Status do plano de rota deve ser informado")
        @Pattern(regexp = "ACTIVE|IN_ACTION|STOPED|CANCELLED",
                message = "Status do plano de rota inválido")
        String status
) {
}
