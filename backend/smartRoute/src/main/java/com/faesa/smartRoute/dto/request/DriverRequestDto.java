package com.faesa.smartRoute.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record DriverRequestDto(
        @NotBlank(message = "Tipo da carteira de motorista deve ser selecionado")
        @Min(message = "Tipo da carteira de motorista deve ter pelo menos 1 caracteries", value = 1L)
        @Max(message = "Tipo da carteira de motorista deve ter no máximo 3 caracteries", value = 3L)
        String driverLicenceLevel,
        @NotBlank(message = "Usuário deve ser selecionado")
        String userId
) {
}
