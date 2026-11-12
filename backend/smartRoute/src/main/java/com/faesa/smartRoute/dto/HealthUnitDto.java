package com.faesa.smartRoute.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record HealthUnitDto(
        @Pattern(regexp = "(?:\\s*|[0-9a-fA-F]{8}-(?:[0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12})",
                message = "Identificador da unidade de saúde inválido")
        String id,

        @NotBlank(message = "Nome da unidade de saúde deve ser informado")
        @Size(max = 50, message = "Nome da unidade de saúde deve ter no máximo 50 caracteres")
        String name,

        @NotBlank(message = "Tipo da unidade de saúde deve ser informado")
        @Pattern(regexp = "HOSPITAL|CLINIC|HEALTH_CENTER",
                message = "Tipo da unidade de saúde inválido")
        String tipo,

        @DecimalMin(value = "-90.0", message = "Latitude deve ser no mínimo -90")
        @DecimalMax(value = "90.0", message = "Latitude deve ser no máximo 90")
        double latitude,

        @DecimalMin(value = "-180.0", message = "Longitude deve ser no mínimo -180")
        @DecimalMax(value = "180.0", message = "Longitude deve ser no máximo 180")
        double longitude
) {
}
