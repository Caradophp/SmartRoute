package com.faesa.smartRoute.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public record EmergencyDto(
        @NotNull(message = "Latitude deve ser informada")
        @DecimalMin(value = "-90.0", message = "Latitude deve ser no mínimo -90")
        @DecimalMax(value = "90.0", message = "Latitude deve ser no máximo 90")
        Double latitude,

        @NotNull(message = "Longitude deve ser informada")
        @DecimalMin(value = "-180.0", message = "Longitude deve ser no mínimo -180")
        @DecimalMax(value = "180.0", message = "Longitude deve ser no máximo 180")
        Double longitude
) {
}
