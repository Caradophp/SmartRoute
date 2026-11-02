package com.faesa.smartRoute.dto;

import com.faesa.smartRoute.model.User;

import java.util.UUID;

public record UserResponseDto(
        UUID id,
        String nome,
        String email,
        long cpf,
        String perfil
) {

    public UserResponseDto(User user) {
        this(user.getId(), user.getNome(), user.getEmail(), user.getCpf(), user.getRole().getNomePerfil());
    }

}
