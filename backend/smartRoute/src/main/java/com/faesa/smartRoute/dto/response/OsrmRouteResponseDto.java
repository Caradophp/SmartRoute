package com.faesa.smartRoute.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OsrmRouteResponseDto(
        String code,
        List<OsrmRoute> routes
) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record OsrmRoute(
            double distance,
            double duration,
            Geometry geometry
    ) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Geometry(
            String type,
            List<List<Double>> coordinates
    ) {
    }
}
