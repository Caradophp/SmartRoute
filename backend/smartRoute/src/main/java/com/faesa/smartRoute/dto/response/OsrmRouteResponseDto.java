package com.faesa.smartRoute.dto.response;

import java.util.List;

public record OsrmRouteResponseDto(
        List<OsrmRoute> routes
) {

    public record OsrmRoute(
            double distance,
            double duration,
            Geometry geometry
    ) {
    }

    public record Geometry(
            List<List<Double>> coordinates
    ) {
    }
}

