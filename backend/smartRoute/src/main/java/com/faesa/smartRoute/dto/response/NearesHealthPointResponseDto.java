package com.faesa.smartRoute.dto.response;

import com.faesa.smartRoute.dto.HealthUnitDto;
import com.faesa.smartRoute.dto.RouteDto;

public record NearesHealthPointResponseDto(
        HealthUnitDto healthUnit,
        RouteDto route
) {
}
