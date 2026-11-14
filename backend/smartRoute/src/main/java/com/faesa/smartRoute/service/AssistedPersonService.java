package com.faesa.smartRoute.service;

import com.faesa.smartRoute.exceptions.RecordNotFoundException;
import com.faesa.smartRoute.model.AssistedPerson;
import com.faesa.smartRoute.repository.AssistedPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class AssistedPersonService {

    @Autowired
    private AssistedPersonRepository repository;

    public List<AssistedPerson> findAll() {
        return repository.findAll();
    }

    public AssistedPerson findById(String id) {
        return repository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RecordNotFoundException("Pessoa assistida não encontrado"));
    }

    public AssistedPerson save(AssistedPerson entity) {
        entity.setId(null);
        return repository.save(entity);
    }

    @Transactional
    public AssistedPerson update(String id, AssistedPerson entity) {
        UUID uuid = UUID.fromString(id);
        if (!repository.existsById(uuid)) {
            throw new RecordNotFoundException("Pessoa assistida não encontrado");
        }
        entity.setId(uuid);
        return repository.save(entity);
    }

    @Transactional
    public void delete(String id) {
        UUID uuid = UUID.fromString(id);
        if (!repository.existsById(uuid)) {
            throw new RecordNotFoundException("Pessoa assistida não encontrado");
        }
        repository.deleteById(uuid);
    }
}
