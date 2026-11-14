package com.faesa.smartRoute.service;

import com.faesa.smartRoute.dto.RoleDto;
import com.faesa.smartRoute.exceptions.RecordNotFoundException;
import com.faesa.smartRoute.model.Role;
import com.faesa.smartRoute.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService extends BaseService {

    @Autowired
    private RoleRepository roleRepository;

    public void save(RoleDto dto) {
        Role role = convertToEntity(dto, Role.class);
        roleRepository.save(role);
    }

    @Transactional
    public void update(String id, RoleDto dto) {
        Optional<Role> roleToUpdateOptional = roleRepository.findById(stringToUuid(id));

        if (roleToUpdateOptional.isEmpty()) {
            throw new RecordNotFoundException("Nenhuma role foi encontrada com esse ID");
        }

        Role role = roleToUpdateOptional.get();

        role.setNomePerfil(dto.nomePerfil());

        roleRepository.save(role);
    }

    public void delete(String id) {
        Optional<Role> roleOptional = roleRepository.findById(stringToUuid(id));

        if (roleOptional.isEmpty()) {
            throw new RecordNotFoundException("Nenhuma role foi encontrada com esse ID");
        }

        roleRepository.delete(roleOptional.get());
    }

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

    @Override
    public <E, D> D convertToDto(E entity, Class<D> dClass) {
        Role role = (Role) entity;
        RoleDto dto = new RoleDto(role.getNomePerfil());
        return (D) dto;
    }

    @Override
    public <E, D> E convertToEntity(D dto, Class<E> dClass) {
        RoleDto myDto = (RoleDto) dto;
        Role role = new Role();
        role.setNomePerfil(myDto.nomePerfil());
        return (E) role;
    }
}
