package com.faesa.smartRoute.controller;

import com.faesa.smartRoute.dto.request.HealthAgentRequestDto;
import com.faesa.smartRoute.service.HealthAgentService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health-agents")
public class HealthAgentController {

    @Autowired
    private HealthAgentService healthAgentService;

    public ResponseEntity<Void> saveAgent(@RequestBody @Valid HealthAgentRequestDto healthAgentRequestDto) {
        healthAgentService.saveAgent(healthAgentRequestDto);
        return ResponseEntity.ok().build();
    }

}
