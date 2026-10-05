package com.faesa.smartRoute.controller;

import com.faesa.smartRoute.dto.ChangePassDto;
import com.faesa.smartRoute.dto.CodeToCheckDto;
import com.faesa.smartRoute.dto.LoginDto;
import com.faesa.smartRoute.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginDto loginDto) {
        return ResponseEntity.ok().body(loginService.login(
                loginDto.email(),
                loginDto.senha()
        ));
    }

    @PostMapping("/email")
    public ResponseEntity<Void> sendEmail(@RequestBody Map<String, String> param) {
        loginService.sendEmail(param.get("email"));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/check")
    public ResponseEntity<Void> checkCode(@RequestBody CodeToCheckDto param) {
        loginService.checkCode(param.codigo(), param.email());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/changePass")
    public ResponseEntity<Void> changePass(@RequestBody ChangePassDto changePassDto) {
        loginService.changePass(changePassDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
