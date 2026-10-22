package com.faesa.smartRoute.service;

import com.faesa.smartRoute.dto.RoleDto;
import com.faesa.smartRoute.exceptions.RecordNotFoundException;
import com.faesa.smartRoute.model.Role;
import com.faesa.smartRoute.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<RoleDto> findAll() {
        return roleRepository.findAll()
                .stream()
                .map(this::converToDto)
                .toList();
    }

    public RoleDto findByNomePerfil(String nomePerfil) {
        Role role = roleRepository.findByNomePerfil(nomePerfil)
                .orElseThrow(() -> new RecordNotFoundException("Perfil não encontrada"));

        return this.converToDto(role);
    }

    private RoleDto converToDto(Role role) {
        return new RoleDto(role.getNomePerfil());
    }
}
