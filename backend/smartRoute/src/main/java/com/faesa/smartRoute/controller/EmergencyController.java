package com.faesa.smartRoute.controller;

import com.faesa.smartRoute.dto.EmergencyDto;
import com.faesa.smartRoute.service.EmergencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/emergencias")
public class EmergencyController {

    @Autowired
    private EmergencyService emergencyService;

    @PostMapping
    public ResponseEntity<Void> criarEmergencia(@RequestBody EmergencyDto emergencyDto) {
        emergencyService.notificarChamadaEmergencia(emergencyDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
