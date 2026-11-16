package com.faesa.smartRoute.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DriverRequestDto(
        @NotBlank(message = "Tipo da carteira de motorista deve ser selecionado")
        @Size(min = 1, max = 3, message = "Tipo de carteira de trabalho deve ter no minimo 1 e no máximo 3 caractéries")
        String driverLicenceLevel,
        @NotBlank(message = "Usuário deve ser selecionado")
        String userId
) {
}
