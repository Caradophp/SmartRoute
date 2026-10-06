package com.faesa.smartRoute.controller;

import com.faesa.smartRoute.model.Route;
import com.faesa.smartRoute.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/routes")
public class RouteController {

    @Autowired
    private RouteService routeService;

    @PostMapping
    public ResponseEntity<Route> createRoute(@RequestBody Route route) {
        return ResponseEntity.ok(routeService.saveRoute(route));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Route>> getRoutesByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(routeService.listRoutesByUserId(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Route> getRoute(@PathVariable Long id) {
        return ResponseEntity.ok(routeService.findRouteById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Route> updateRoute(@PathVariable Long id, @RequestBody Route route) {
        return ResponseEntity.ok(routeService.updateRoute(id, route));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoute(@PathVariable Long id) {
        routeService.deleteRoute(id);
        return ResponseEntity.noContent().build();
    }
}