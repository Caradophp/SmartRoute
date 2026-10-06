package com.faesa.smartRoute.service;

import com.faesa.smartRoute.model.Route;
import com.faesa.smartRoute.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class RouteService {

    @Autowired
    private RouteRepository routeRepository;

    public Route saveRoute(Route route) {
        return routeRepository.save(route);
    }

    public List<Route> listRoutesByUserId(Long userId) {
        return routeRepository.findByUserId(userId);
    }

    public Route findRouteById(Long id) {
        return routeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rota não encontrada com o ID: " + id));
    }

    @Transactional
    public Route updateRoute(Long id, Route routeDetails) {
        Route route = findRouteById(id);
        route.setName(routeDetails.getName());
        route.setOrigin(routeDetails.getOrigin());
        route.setDestination(routeDetails.getDestination());
        route.setDistance(routeDetails.getDistance());
        return routeRepository.save(route);
    }

    public void deleteRoute(Long id) {
        if (!routeRepository.existsById(id)) {
            throw new RuntimeException("Rota não encontrada para exclusão");
        }
        routeRepository.deleteById(id);
    }
}