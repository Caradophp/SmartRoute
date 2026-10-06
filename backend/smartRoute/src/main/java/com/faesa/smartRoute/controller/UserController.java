package com.faesa.smartRoute.controller;

import com.faesa.smartRoute.dto.AppUserDto;
import com.faesa.smartRoute.model.AppUser;
import com.faesa.smartRoute.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody AppUserDto appUserDto) {
        userService.cadastrarUsuario(appUserDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AppUser>> listAllUsers() {
        // Assumindo que o userService tenha ou possa ter um método de listar todos
        // Caso contrário, pode ser implementada a lógica no UserService
        return ResponseEntity.ok(userService.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppUser> getUserById(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppUser> updateUser(@PathVariable UUID id, @RequestBody AppUser user) {
        return ResponseEntity.ok(userService.update(id, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}