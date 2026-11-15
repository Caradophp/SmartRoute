package com.faesa.smartRoute.dto.request;

import java.time.LocalDate;

public record FieldVisitRequestDto(
        LocalDate dataVisita,
        String observacao,
        String personId,
        String routePlanId
) {
}
