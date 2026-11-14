package com.faesa.smartRoute.service;

import com.faesa.smartRoute.exceptions.RecordNotFoundException;
import com.faesa.smartRoute.model.Territory;
import com.faesa.smartRoute.repository.TerritoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class TerritoryService {

    @Autowired
    private TerritoryRepository repository;

    public List<Territory> findAll() {
        return repository.findAll();
    }

    public Territory findById(String id) {
        return repository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RecordNotFoundException("Território não encontrado"));
    }

    public Territory save(Territory entity) {
        entity.setId(null);
        return repository.save(entity);
    }

    @Transactional
    public Territory update(String id, Territory entity) {
        UUID uuid = UUID.fromString(id);
        if (!repository.existsById(uuid)) {
            throw new RecordNotFoundException("Território não encontrado");
        }
        entity.setId(uuid);
        return repository.save(entity);
    }

    @Transactional
    public void delete(String id) {
        UUID uuid = UUID.fromString(id);
        if (!repository.existsById(uuid)) {
            throw new RecordNotFoundException("Território não encontrado");
        }
        repository.deleteById(uuid);
    }
}
