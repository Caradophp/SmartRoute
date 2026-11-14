package com.faesa.smartRoute.service;

import com.faesa.smartRoute.exceptions.RecordNotFoundException;
import com.faesa.smartRoute.model.RoutePlan;
import com.faesa.smartRoute.repository.RoutePlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class RoutePlanService {

    @Autowired
    private RoutePlanRepository repository;

    public List<RoutePlan> findAll() {
        return repository.findAll();
    }

    public RoutePlan findById(String id) {
        return repository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RecordNotFoundException("Plano de rota não encontrado"));
    }

    public RoutePlan save(RoutePlan entity) {
        entity.setId(null);
        return repository.save(entity);
    }

    @Transactional
    public RoutePlan update(String id, RoutePlan entity) {
        UUID uuid = UUID.fromString(id);
        if (!repository.existsById(uuid)) {
            throw new RecordNotFoundException("Plano de rota não encontrado");
        }
        entity.setId(uuid);
        return repository.save(entity);
    }

    @Transactional
    public void delete(String id) {
        UUID uuid = UUID.fromString(id);
        if (!repository.existsById(uuid)) {
            throw new RecordNotFoundException("Plano de rota não encontrado");
        }
        repository.deleteById(uuid);
    }
}
