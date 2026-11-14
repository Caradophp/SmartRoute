package com.faesa.smartRoute.controller;

import com.faesa.smartRoute.dto.RoleDto;
import com.faesa.smartRoute.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public ResponseEntity<List<RoleDto>> findAll() {
        List<RoleDto> allRoles = roleService.findAll();
        return ResponseEntity.ok(allRoles);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> save(@RequestBody @Valid RoleDto roleDto) {
        roleService.save(roleDto);
        return ResponseEntity.ok().build();
    }


    @PutMapping("/alter/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> update(@PathVariable String id, @RequestBody @Valid RoleDto roleDto) {
        roleService.update(id, roleDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        roleService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{perfil}")
    public ResponseEntity<RoleDto> findByNomePerfil(@PathVariable("perfil") String nomePerfil) {
        RoleDto roleDto = roleService.findByNomePerfil(nomePerfil);
        return ResponseEntity.ok(roleDto);
    }

}
