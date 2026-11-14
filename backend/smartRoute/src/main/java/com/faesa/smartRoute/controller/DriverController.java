package com.faesa.smartRoute.controller;

import com.faesa.smartRoute.dto.request.DriverRequestDto;
import com.faesa.smartRoute.service.DriverService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/drivers")
public class DriverController {

    @Autowired
    private DriverService driverService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> save(@RequestBody @Valid DriverRequestDto driverRequestDto) {
        driverService.save(driverRequestDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/alter/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> update(@PathVariable String id, @RequestBody @Valid DriverRequestDto driverRequestDto) {
        driverService.update(id, driverRequestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        driverService.delete(id);
        return ResponseEntity.ok().build();
    }


}
