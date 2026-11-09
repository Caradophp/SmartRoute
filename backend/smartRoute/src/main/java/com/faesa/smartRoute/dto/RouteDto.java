package com.faesa.smartRoute.dto;

import java.util.List;

public record RouteDto(
        double distance,
        double duration,
        List<List<Double>> coordinates
) {
}
