package com.faesa.smartRoute.service;

import com.faesa.smartRoute.dto.response.OsrmRouteResponseDto;
import com.faesa.smartRoute.exceptions.RecordNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Locale;

@Service
public class RoutingService {

    @Autowired
    private RestClient restClient;

    private ObjectMapper objectMapper = new ObjectMapper();


    public OsrmRouteResponseDto.OsrmRoute calculateRoute(
            double userLatitude,
            double userLongitude,
            double destinationLatitude,
            double destinationLongitude) {

        String coordinates = String.format(
                Locale.US,
                "%f,%f;%f,%f",
                userLongitude,
                userLatitude,
                destinationLongitude,
                destinationLatitude
        );

        String json = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/route/v1/driving/" + coordinates)
                        .queryParam("overview", "full")
                        .queryParam("geometries", "geojson")
                        .build())
                .header("Accept-Encoding", "identity")
                .retrieve()
                .body(String.class);

        OsrmRouteResponseDto response = null;

        try {
            response = objectMapper.readValue(json, OsrmRouteResponseDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        if (response == null || response.routes() == null || response.routes().isEmpty()) {
            throw new RecordNotFoundException(
                    "Não foi encontrada nenhuma rota para essas coordenadas"
            );
        }

        return response.routes().getFirst();
    }

}
