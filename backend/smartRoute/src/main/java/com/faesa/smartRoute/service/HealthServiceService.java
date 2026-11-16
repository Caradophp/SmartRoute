package com.faesa.smartRoute.service;

import com.faesa.smartRoute.dto.HealthServiceDto;
import com.faesa.smartRoute.exceptions.RecordNotFoundException;
import com.faesa.smartRoute.model.HealthService;
import com.faesa.smartRoute.repository.HealthServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class HealthServiceService extends BaseService {

    @Autowired
    private HealthServiceRepository repository;

    public List<HealthServiceDto> findAll() {
        return repository.findAll()
                .stream().map(healthService -> convertToDto(healthService, HealthServiceDto.class))
                .toList();
    }

    public HealthServiceDto findById(String id) {
        return repository.findById(UUID.fromString(id))
                .map(healthService -> convertToDto(healthService, HealthServiceDto.class))
                .orElseThrow(() -> new RecordNotFoundException("Serviço de saúde não encontrado"));
    }

    public void save(HealthServiceDto dto) {
        HealthService entity = convertToEntity(dto, HealthService.class);
        repository.save(entity);
    }

    @Transactional
    public void update(String id, HealthServiceDto dto) {
        UUID uuid = UUID.fromString(id);
        if (!repository.existsById(uuid)) {
            throw new RecordNotFoundException("Serviço de saúde não encontrado");
        }
        HealthService entity = convertToEntity(dto, HealthService.class);
        entity.setId(uuid);
        repository.save(entity);
    }

    @Transactional
    public void delete(String id) {
        UUID uuid = UUID.fromString(id);
        if (!repository.existsById(uuid)) {
            throw new RecordNotFoundException("Serviço de saúde não encontrado");
        }
        repository.deleteById(uuid);
    }

    @Override
    public <E, D> D convertToDto(E entity, Class<D> dClass) {
        HealthService healthService = (HealthService) entity;

        HealthServiceDto healthServiceDto
                = new HealthServiceDto(uuidToString(healthService.getId()), healthService.getNome(), healthService.getDescricao());

        return (D) healthServiceDto;
    }

    @Override
    public <E, D> E convertToEntity(D dto, Class<E> dClass) {
        HealthServiceDto healthServiceDto = (HealthServiceDto) dto;

        HealthService healthService = new HealthService();
        healthService.setId(stringToUuid(healthServiceDto.id()));
        healthService.setNome(healthServiceDto.nome());
        healthService.setDescricao(healthServiceDto.descricao());

        return (E) healthService;
    }
}
