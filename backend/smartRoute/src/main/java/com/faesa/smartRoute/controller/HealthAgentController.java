package com.faesa.smartRoute.controller;

import com.faesa.smartRoute.dto.request.HealthAgentRequestDto;
import com.faesa.smartRoute.dto.response.HealthAgentResponseDto;
import com.faesa.smartRoute.service.HealthAgentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/health-agents")
public class HealthAgentController {

    @Autowired
    private HealthAgentService healthAgentService;

    @GetMapping("/listar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<HealthAgentResponseDto>> findAll() {
        List<HealthAgentResponseDto> all = healthAgentService.findAll();
        return ResponseEntity.ok(all);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> save(@RequestBody @Valid HealthAgentRequestDto healthAgentRequestDto) {
        healthAgentService.save(healthAgentRequestDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/alter/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> update(@PathVariable String id, @RequestBody @Valid HealthAgentRequestDto healthAgentRequestDto) {
        healthAgentService.update(id, healthAgentRequestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        healthAgentService.delede(id);
        return ResponseEntity.ok().build();
    }

}
