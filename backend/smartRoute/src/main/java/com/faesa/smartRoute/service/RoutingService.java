package com.faesa.smartRoute.service;

import com.faesa.smartRoute.dto.response.OsrmRouteResponseDto;
import com.faesa.smartRoute.exceptions.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class RoutingService {

    @Autowired
    private RestClient restClient;


    public OsrmRouteResponseDto.OsrmRoute calculateRoute(double userLatitude,
                                                         double userLongitude,
                                                         double destinationLatitude,
                                                         double destinationLongitude) {

        String coordinates = userLongitude + "," + userLatitude + ";" + destinationLongitude + "," + destinationLatitude;

        OsrmRouteResponseDto response = restClient.get()
                .uri("/route/v1/driving/{coordinates}?overview=full&geometries=geojson", coordinates)
                .retrieve()
                .body(OsrmRouteResponseDto.class);

        if (response == null || response.routes() == null || response.routes().isEmpty()) {
            throw new RecordNotFoundException("Não foi encontrado nenhuma rota para essas coordenadas");
        }

        return response.routes().getFirst();
    }
}
