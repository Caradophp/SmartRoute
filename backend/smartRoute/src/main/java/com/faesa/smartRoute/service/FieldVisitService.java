package com.faesa.smartRoute.service;

import com.faesa.smartRoute.dto.request.FieldVisitRequestDto;
import com.faesa.smartRoute.dto.response.FieldVisitResponseDto;
import com.faesa.smartRoute.exceptions.RecordNotFoundException;
import com.faesa.smartRoute.model.AssistedPerson;
import com.faesa.smartRoute.model.FieldVisit;
import com.faesa.smartRoute.model.RoutePlan;
import com.faesa.smartRoute.repository.AssistedPersonRepository;
import com.faesa.smartRoute.repository.FieldVisitRepository;
import com.faesa.smartRoute.repository.RoutePlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
public class FieldVisitService extends BaseService {

    @Autowired
    private FieldVisitRepository repository;

    @Autowired
    private AssistedPersonRepository assistedPersonRepository;

    @Autowired
    private RoutePlanRepository routePlanRepository;

    public List<FieldVisitResponseDto> findAll() {
        return repository.findAll()
                .stream().map(fieldVisit -> convertToDto(fieldVisit, FieldVisitResponseDto.class))
                .toList();
    }

    public FieldVisitResponseDto findById(String id) {
        return repository.findById(UUID.fromString(id))
                .map(fieldVisit -> convertToDto(fieldVisit, FieldVisitResponseDto.class))
                .orElseThrow(() -> new RecordNotFoundException("Visita de campo não encontrado"));
    }

    public void save(FieldVisitRequestDto dto) {
        FieldVisit entity = convertToEntity(dto, FieldVisit.class);
        repository.save(entity);
    }

    @Transactional
    public void update(String id, FieldVisitRequestDto dto) {
        UUID uuid = UUID.fromString(id);
        if (!repository.existsById(uuid)) {
            throw new RecordNotFoundException("Visita de campo não encontrado");
        }

        FieldVisit entity = convertToEntity(dto, FieldVisit.class);
        entity.setId(uuid);
        repository.save(entity);
    }

    @Transactional
    public void delete(String id) {
        UUID uuid = UUID.fromString(id);
        if (!repository.existsById(uuid)) {
            throw new RecordNotFoundException("Visita de campo não encontrado");
        }
        repository.deleteById(uuid);
    }

    @Override
    public <E, D> D convertToDto(E entity, Class<D> dClass) {
        FieldVisit fieldVisit = (FieldVisit) entity;

        String format = fieldVisit.getDataVisita().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        FieldVisitResponseDto fieldVisitResponseDto
                = new FieldVisitResponseDto(uuidToString(fieldVisit.getId()), format, fieldVisit.getObservacao());

        return (D) fieldVisitResponseDto;
    }

    @Override
    public <E, D> E convertToEntity(D dto, Class<E> dClass) {
        FieldVisitRequestDto fieldVisitRequestDto = (FieldVisitRequestDto) dto;

        AssistedPerson assistedPerson = assistedPersonRepository.findById(stringToUuid(fieldVisitRequestDto.personId())).orElseThrow(RecordNotFoundException::new);
        RoutePlan routePlan = routePlanRepository.findById(stringToUuid(fieldVisitRequestDto.routePlanId())).orElseThrow(RecordNotFoundException::new);

        FieldVisit fieldVisit = new FieldVisit();
        fieldVisit.setDataVisita(fieldVisitRequestDto.dataVisita());
        fieldVisit.setObservacao(fieldVisitRequestDto.observacao());
        fieldVisit.setAssistedPerson(assistedPerson);
        fieldVisit.setRoutePlan(routePlan);

        return (E) routePlan;
    }
}
