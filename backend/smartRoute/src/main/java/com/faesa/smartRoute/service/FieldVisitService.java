package com.faesa.smartRoute.service;

import com.faesa.smartRoute.exceptions.RecordNotFoundException;
import com.faesa.smartRoute.model.FieldVisit;
import com.faesa.smartRoute.repository.FieldVisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class FieldVisitService {

    @Autowired
    private FieldVisitRepository repository;

    public List<FieldVisit> findAll() {
        return repository.findAll();
    }

    public FieldVisit findById(String id) {
        return repository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RecordNotFoundException("Visita de campo não encontrado"));
    }

    public FieldVisit save(FieldVisit entity) {
        entity.setId(null);
        return repository.save(entity);
    }

    @Transactional
    public FieldVisit update(String id, FieldVisit entity) {
        UUID uuid = UUID.fromString(id);
        if (!repository.existsById(uuid)) {
            throw new RecordNotFoundException("Visita de campo não encontrado");
        }
        entity.setId(uuid);
        return repository.save(entity);
    }

    @Transactional
    public void delete(String id) {
        UUID uuid = UUID.fromString(id);
        if (!repository.existsById(uuid)) {
            throw new RecordNotFoundException("Visita de campo não encontrado");
        }
        repository.deleteById(uuid);
    }
}
