package com.faesa.smartRoute.dto;

import java.time.LocalDate;

public record RoutePlanDto(
        String id,
        String nome,
        LocalDate data,
        String status
) {
}
