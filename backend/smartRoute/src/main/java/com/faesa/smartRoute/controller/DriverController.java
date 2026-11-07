package com.faesa.smartRoute.controller;

import com.faesa.smartRoute.dto.request.DriverRequestDto;
import com.faesa.smartRoute.service.DriverService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/drivers")
public class DriverController {

    @Autowired
    private DriverService driverService;

    @PostMapping
    private ResponseEntity<Void> saveDriver(@RequestBody @Valid DriverRequestDto driverRequestDto) {
        driverService.saveDriver(driverRequestDto);
        return ResponseEntity.ok().build();
    }

}
