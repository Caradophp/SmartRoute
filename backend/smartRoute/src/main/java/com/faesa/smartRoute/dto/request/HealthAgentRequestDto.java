package com.faesa.smartRoute.dto.request;

import com.faesa.smartRoute.annotation.Phone;
import jakarta.validation.constraints.NotBlank;

public record HealthAgentRequestDto(
        @NotBlank(message = "Telefone deve ser informado")
        @Phone(message = "Número de telefone inválido")
        String telefone,
        @NotBlank(message = "Usuário deve ser informado")
        String userId
) {
}
