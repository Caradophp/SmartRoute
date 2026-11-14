package com.faesa.smartRoute.service;

import com.faesa.smartRoute.exceptions.RecordNotFoundException;
import com.faesa.smartRoute.model.Code;
import com.faesa.smartRoute.repository.CodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class CodeService {

    @Autowired
    private CodeRepository repository;

    public List<Code> findAll() {
        return repository.findAll();
    }

    public Code findById(String id) {
        return repository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RecordNotFoundException("Código não encontrado"));
    }

    public Code save(Code entity) {
        entity.setId(null);
        return repository.save(entity);
    }

    @Transactional
    public Code update(String id, Code entity) {
        UUID uuid = UUID.fromString(id);
        if (!repository.existsById(uuid)) {
            throw new RecordNotFoundException("Código não encontrado");
        }
        entity.setId(uuid);
        return repository.save(entity);
    }

    @Transactional
    public void delete(String id) {
        UUID uuid = UUID.fromString(id);
        if (!repository.existsById(uuid)) {
            throw new RecordNotFoundException("Código não encontrado");
        }
        repository.deleteById(uuid);
    }
}
