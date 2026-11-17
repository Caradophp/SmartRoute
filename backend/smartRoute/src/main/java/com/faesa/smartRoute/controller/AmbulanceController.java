
package com.faesa.smartRoute.controller;

import com.faesa.smartRoute.dto.AmbulanceDto;
import com.faesa.smartRoute.dto.response.AmbulanceResponseDto;
import com.faesa.smartRoute.service.AmbulanceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ambulances")
public class AmbulanceController {

    @Autowired
    private AmbulanceService ambulanceService;

    @GetMapping("/listar")
    @PreAuthorize("hasRole('ADMIN') || hasRole('DRIVER')")
    public ResponseEntity<List<AmbulanceResponseDto>> findAll() {
        List<AmbulanceResponseDto> all = ambulanceService.findAll();
        return ResponseEntity.ok(all);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> save(@RequestBody @Valid AmbulanceDto ambulanceDto) {
        ambulanceService.save(ambulanceDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/alter/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> update(
            @PathVariable String id,
            @RequestBody @Valid AmbulanceDto ambulanceDto) {
        ambulanceService.update(id, ambulanceDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        ambulanceService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/defective")
    @PreAuthorize("hasRole('DRIVER')")
    public ResponseEntity<Void> regiterDefectiveVehicle(@RequestBody Map<String, String> body) {
        ambulanceService.regiterDefectiveVehicle(body.get("id"), body.get("vehicleProblem"));
        return ResponseEntity.ok().build();
    }
}
