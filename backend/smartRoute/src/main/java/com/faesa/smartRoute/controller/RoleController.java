package com.faesa.smartRoute.controller;

import com.faesa.smartRoute.dto.RoleDto;
import com.faesa.smartRoute.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/{perfil}")
    public ResponseEntity<RoleDto> findByNomePerfil(@PathVariable("perfil") String nomePerfil) {
        RoleDto roleDto = roleService.findByNomePerfil(nomePerfil);
        return ResponseEntity.ok(roleDto);
    }

}
