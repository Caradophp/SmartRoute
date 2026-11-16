package com.faesa.smartRoute.controller;

import com.faesa.smartRoute.dto.HealthUnitDto;
import com.faesa.smartRoute.dto.response.NearesHealthPointResponseDto;
import com.faesa.smartRoute.service.HealthUnitService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("health-unit")
public class HealthUnitController {

    @Autowired
    private HealthUnitService healthUnitService;

    @GetMapping("/listar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<HealthUnitDto>> findAll() {
        List<HealthUnitDto> all = healthUnitService.findAll();
        return ResponseEntity.ok(all);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> save(@RequestBody @Valid HealthUnitDto healthUnitDto) {
        healthUnitService.save(healthUnitDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/alter/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> update(@PathVariable String id, @RequestBody @Valid HealthUnitDto healthUnitDto) {
        healthUnitService.update(id, healthUnitDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        healthUnitService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/nearest")
    public ResponseEntity<NearesHealthPointResponseDto> findNearest(
            @RequestParam double latitude,
            @RequestParam double longitude
    ) {

        if (!Double.isFinite(latitude) ||
                !Double.isFinite(longitude) ||
                latitude < -90 || latitude > 90 ||
                longitude < -180 || longitude > 180) {

            return ResponseEntity.badRequest().build();
        }

        NearesHealthPointResponseDto response =
                healthUnitService.findNearesHealthPoint(
                        latitude,
                        longitude
                );

        return ResponseEntity.ok(response);
    }

}
