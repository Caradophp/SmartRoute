package com.faesa.smartRoute.service;

import com.faesa.smartRoute.exceptions.RecordNotFoundException;
import com.faesa.smartRoute.model.Adress;
import com.faesa.smartRoute.repository.AdressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class AdressService {

    @Autowired
    private AdressRepository repository;

    public List<Adress> findAll() {
        return repository.findAll();
    }

    public Adress findById(String id) {
        return repository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RecordNotFoundException("Adress não encontrado"));
    }

    public Adress save(Adress entity) {
        entity.setId(null);
        return repository.save(entity);
    }

    @Transactional
    public Adress update(String id, Adress entity) {
        UUID uuid = UUID.fromString(id);
        if (!repository.existsById(uuid)) {
            throw new RecordNotFoundException("Adress não encontrado");
        }
        entity.setId(uuid);
        return repository.save(entity);
    }

    @Transactional
    public void delete(String id) {
        UUID uuid = UUID.fromString(id);
        if (!repository.existsById(uuid)) {
            throw new RecordNotFoundException("Adress não encontrado");
        }
        repository.deleteById(uuid);
    }
}
