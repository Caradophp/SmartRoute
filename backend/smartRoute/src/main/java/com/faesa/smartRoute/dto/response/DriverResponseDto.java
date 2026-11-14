package com.faesa.smartRoute.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DriverResponseDto(
        String id,
        String driverLicenceLevel,
        @JsonProperty("nome")
        String driverName,
        String driverEmail
) {
}
