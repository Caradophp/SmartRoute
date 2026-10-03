package com.faesa.smartRoute.controller;

import com.faesa.smartRoute.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> params) {
        return ResponseEntity.ok().body(loginService.login(
                params.get("email"),
                params.get("senha")
        ));
    }

}
