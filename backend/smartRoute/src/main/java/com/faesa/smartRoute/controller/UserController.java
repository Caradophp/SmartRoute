package com.faesa.smartRoute.controller;

import com.faesa.smartRoute.dto.AppUserDto;
import com.faesa.smartRoute.dto.UserRequestDto;
import com.faesa.smartRoute.dto.UserResponseDto;
import com.faesa.smartRoute.model.AppUser;
import com.faesa.smartRoute.model.User;
import com.faesa.smartRoute.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Void> register(@RequestBody UserRequestDto userRequestDto) {
        userService.cadastrarUsuario(userRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/listar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDto>> listAllUsers() {
        return ResponseEntity.ok(userService.findAll().stream()
                .map(this::convertToDto)
                .toList());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable UUID id) {
        return ResponseEntity.ok(convertToDto(userService.findById(id)));
    }

    @PutMapping("/alter/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable UUID id, @RequestBody UserRequestDto user) {
        return ResponseEntity.ok(convertToDto(userService.update(id, user)));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search/{param}")
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponseDto> search(@PathVariable String param) {
        return userService.search(param)
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    private UserResponseDto convertToDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getNome(),
                user.getEmail(),
                user.getCpf(),

                user.getRole() != null ? user.getRole().getNomePerfil() : null
        );
    }
}