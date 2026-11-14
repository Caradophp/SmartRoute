package com.faesa.smartRoute.service;

import com.faesa.smartRoute.exceptions.RecordNotFoundException;
import com.faesa.smartRoute.model.HealthService;
import com.faesa.smartRoute.repository.HealthServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class HealthServiceService {

    @Autowired
    private HealthServiceRepository repository;

    public List<HealthService> findAll() {
        return repository.findAll();
    }

    public HealthService findById(String id) {
        return repository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RecordNotFoundException("Serviço de saúde não encontrado"));
    }

    public HealthService save(HealthService entity) {
        entity.setId(null);
        return repository.save(entity);
    }

    @Transactional
    public HealthService update(String id, HealthService entity) {
        UUID uuid = UUID.fromString(id);
        if (!repository.existsById(uuid)) {
            throw new RecordNotFoundException("Serviço de saúde não encontrado");
        }
        entity.setId(uuid);
        return repository.save(entity);
    }

    @Transactional
    public void delete(String id) {
        UUID uuid = UUID.fromString(id);
        if (!repository.existsById(uuid)) {
            throw new RecordNotFoundException("Serviço de saúde não encontrado");
        }
        repository.deleteById(uuid);
    }
}
