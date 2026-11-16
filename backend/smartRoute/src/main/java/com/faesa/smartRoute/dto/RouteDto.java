package com.faesa.smartRoute.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.util.List;

public record RouteDto(
        @PositiveOrZero(message = "Distância não pode ser negativa")
        double distance,

        @PositiveOrZero(message = "Duração não pode ser negativa")
        double duration,

        @NotEmpty(message = "Coordenadas da rota devem ser informadas")
        List<@NotNull @Size(min = 2, max = 2, message = "Cada coordenada deve conter longitude e latitude")
                List<@NotNull Double>> coordinates
) {
}
