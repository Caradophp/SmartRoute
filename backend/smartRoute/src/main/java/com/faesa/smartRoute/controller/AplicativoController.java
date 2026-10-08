package com.faesa.smartRoute.controller;

import com.faesa.smartRoute.dto.AppUserDto;
import com.faesa.smartRoute.dto.ChangePassDto;
import com.faesa.smartRoute.service.Aplicativo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/aplicativo")
public class AplicativoController {

    @Autowired
    private Aplicativo aplicativo;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> cadastrarUsuarioAplicatio(@RequestBody AppUserDto appUserDto) {
        aplicativo.cadastrarUsuario(appUserDto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> trocarSenha(@RequestBody ChangePassDto changePassDto) {
        aplicativo.trocarSenha(changePassDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
