package com.faesa.smartRoute.service;

import com.faesa.smartRoute.dto.RoutePlanDto;
import com.faesa.smartRoute.exceptions.RecordNotFoundException;
import com.faesa.smartRoute.model.RoutePlan;
import com.faesa.smartRoute.model.enums.RoutePlanStatus;
import com.faesa.smartRoute.repository.RoutePlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class RoutePlanService extends BaseService{

    @Autowired
    private RoutePlanRepository repository;

    public List<RoutePlanDto> findAll() {
        return repository.findAll()
                .stream().map(routePlan -> convertToDto(routePlan, RoutePlanDto.class))
                .toList();
    }

    public RoutePlanDto findById(String id) {
        return repository.findById(UUID.fromString(id))
                .map(routePlan -> convertToDto(routePlan, RoutePlanDto.class))
                .orElseThrow(() -> new RecordNotFoundException("Plano de rota não encontrado"));
    }

    public void save(RoutePlanDto dto) {
        RoutePlan entity = convertToEntity(dto, RoutePlan.class);
        repository.save(entity);
    }

    @Transactional
    public void update(String id, RoutePlanDto dto) {
        UUID uuid = UUID.fromString(id);
        if (!repository.existsById(uuid)) {
            throw new RecordNotFoundException("Plano de rota não encontrado");
        }
        RoutePlan entity = convertToEntity(dto, RoutePlan.class);
        entity.setId(uuid);
        repository.save(entity);
    }

    @Transactional
    public void delete(String id) {
        UUID uuid = UUID.fromString(id);
        if (!repository.existsById(uuid)) {
            throw new RecordNotFoundException("Plano de rota não encontrado");
        }
        repository.deleteById(uuid);
    }

    @Override
    public <E, D> D convertToDto(E entity, Class<D> dClass) {
        RoutePlan routePlan = (RoutePlan) entity;

        RoutePlanDto routePlanDto
                = new RoutePlanDto(uuidToString(routePlan.getId()), routePlan.getNome(), routePlan.getData(), routePlan.getStatus().name());

        return (D) routePlanDto;
    }

    @Override
    public <E, D> E convertToEntity(D dto, Class<E> dClass) {
        RoutePlanDto routePlanDto = (RoutePlanDto) dto;

        RoutePlan routePlan = new RoutePlan();
        routePlan.setId(stringToUuid(routePlanDto.id()));
        routePlan.setNome(routePlanDto.nome());
        routePlan.setData(routePlanDto.data());
        routePlan.setStatus(RoutePlanStatus.valueOf(routePlanDto.status()));

        return (E) routePlan;
    }
}
