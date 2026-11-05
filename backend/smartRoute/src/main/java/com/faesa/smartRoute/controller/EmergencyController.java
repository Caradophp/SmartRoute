package com.faesa.smartRoute.controller;

import com.faesa.smartRoute.dto.AmbulanceDto;
import com.faesa.smartRoute.dto.EmergencyDispachDto;
import com.faesa.smartRoute.dto.EmergencyDto;
import com.faesa.smartRoute.service.EmergencyService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

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

    @PostMapping("/ambulance")
    public ResponseEntity<Void> callAmbulance(@RequestBody EmergencyDispachDto emergencyDispachDto) {
        emergencyService.callAmbulanceNow(emergencyDispachDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/is-necessary")
    public ResponseEntity<Set<AmbulanceDto>> checkIfIsNecessarySendAnAmbulance(HttpServletRequest request) {

        String id = request.getParameter("id");
        String longitude = request.getParameter("longitude");
        String latitude = request.getParameter("latitude");

        Set<AmbulanceDto> ambulanceDtos = emergencyService.checkIfIsNecessary(id, Double.parseDouble(longitude), Double.parseDouble(latitude));

        return ResponseEntity.ok(ambulanceDtos);
    }

}
