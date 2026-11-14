package com.faesa.smartRoute.controller;

import com.faesa.smartRoute.model.AssistedPerson;
import com.faesa.smartRoute.service.AssistedPersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assisted-persons")
public class AssistedPersonController {

    @Autowired
    private AssistedPersonService service;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AssistedPerson>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AssistedPerson> findById(@PathVariable String id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AssistedPerson> save(@RequestBody @Valid AssistedPerson entity) {
        return ResponseEntity.ok(service.save(entity));
    }

    @PutMapping("/alter/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AssistedPerson> update(@PathVariable String id, @RequestBody @Valid AssistedPerson entity) {
        return ResponseEntity.ok(service.update(id, entity));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
