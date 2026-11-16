package com.faesa.smartRoute.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record FieldVisitRequestDto(
        @NotNull(message = "Data da visita deve ser informada")
        LocalDate dataVisita,

        @NotBlank(message = "Observação deve ser informada")
        @Size(max = 50, message = "Observação deve ter no máximo 50 caracteres")
        String observacao,

        @NotBlank(message = "Pessoa assistida deve ser informada")
        @Pattern(regexp = "[0-9a-fA-F]{8}-(?:[0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}",
                message = "Identificador da pessoa assistida inválido")
        String personId,

        @NotBlank(message = "Plano de rota deve ser informado")
        @Pattern(regexp = "[0-9a-fA-F]{8}-(?:[0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}",
                message = "Identificador do plano de rota inválido")
        String routePlanId
) {
}
