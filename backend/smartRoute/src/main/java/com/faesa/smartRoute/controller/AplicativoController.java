package com.faesa.smartRoute.controller;

import com.faesa.smartRoute.dto.AppUserDto;
import com.faesa.smartRoute.service.Aplicativo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aplicativo")
public class AplicativoController {

    @Autowired
    private Aplicativo aplicativo;

    @PostMapping
    public ResponseEntity<Void> cadastrarUsuarioAplicatio(@RequestBody AppUserDto appUserDto) {
        aplicativo.cadastrarUsuario(appUserDto);
        return ResponseEntity.ok().build();
    }

}
