package com.faesa.smartRoute.controller;

import com.faesa.smartRoute.dto.request.FieldVisitRequestDto;
import com.faesa.smartRoute.dto.response.FieldVisitResponseDto;
import com.faesa.smartRoute.service.FieldVisitService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/field-visits")
public class FieldVisitController {

    @Autowired
    private FieldVisitService fieldVisitService;

    @GetMapping("/listar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<FieldVisitResponseDto>> findAll() {
        return ResponseEntity.ok(fieldVisitService.findAll());
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> save(@RequestBody @Valid FieldVisitRequestDto fieldVisitRequestDto) {
        fieldVisitService.save(fieldVisitRequestDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/alter/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> update(@PathVariable String id, @RequestBody @Valid FieldVisitRequestDto fieldVisitRequestDto) {
        fieldVisitService.update(id, fieldVisitRequestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        fieldVisitService.delete(id);
        return ResponseEntity.ok().build();
    }


}
