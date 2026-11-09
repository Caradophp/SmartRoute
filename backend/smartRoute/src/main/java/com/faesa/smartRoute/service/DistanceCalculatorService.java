package com.faesa.smartRoute.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DistanceCalculatorService {

    private static final double EARTH_RADIUS = 6371.0;

    public BigDecimal calculateDistance(
            BigDecimal latitude1,
            BigDecimal longitude1,
            BigDecimal latitude2,
            BigDecimal longitude2
    ) {

        double lat1 = latitude1.doubleValue();
        double lon1 = longitude1.doubleValue();

        double lat2 = latitude2.doubleValue();
        double lon2 = longitude2.doubleValue();

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a =
                Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                        +
                        Math.cos(Math.toRadians(lat1))
                                *
                                Math.cos(Math.toRadians(lat2))
                                *
                                Math.sin(lonDistance / 2)
                                *
                                Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(
                Math.sqrt(a),
                Math.sqrt(1 - a)
        );

        double distance = EARTH_RADIUS * c;

        return BigDecimal.valueOf(distance);
    }
}
