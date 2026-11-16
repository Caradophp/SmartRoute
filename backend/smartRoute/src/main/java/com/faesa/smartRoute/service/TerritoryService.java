package com.faesa.smartRoute.service;

import com.faesa.smartRoute.dto.TerritoryDto;
import com.faesa.smartRoute.exceptions.RecordNotFoundException;
import com.faesa.smartRoute.model.Territory;
import com.faesa.smartRoute.model.enums.TerritoryType;
import com.faesa.smartRoute.repository.TerritoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class TerritoryService extends BaseService {

    @Autowired
    private TerritoryRepository repository;

    public List<TerritoryDto> findAll() {
        return repository.findAll()
                .stream().map(territory -> convertToDto(territory, TerritoryDto.class))
                .toList();
    }

    public TerritoryDto findById(String id) {
        return repository.findById(UUID.fromString(id))
                .map(territory -> convertToDto(territory, TerritoryDto.class))
                .orElseThrow(() -> new RecordNotFoundException("Território não encontrado"));
    }

    public void save(TerritoryDto dto) {
        Territory entity = convertToEntity(dto, Territory.class);
        repository.save(entity);
    }

    @Transactional
    public void update(String id, TerritoryDto dto) {
        UUID uuid = UUID.fromString(id);
        if (!repository.existsById(uuid)) {
            throw new RecordNotFoundException("Território não encontrado");
        }
        Territory entity = convertToEntity(dto, Territory.class);
        entity.setId(uuid);
        repository.save(entity);
    }

    @Transactional
    public void delete(String id) {
        UUID uuid = UUID.fromString(id);
        if (!repository.existsById(uuid)) {
            throw new RecordNotFoundException("Território não encontrado");
        }
        repository.deleteById(uuid);
    }

    @Override
    public <E, D> D convertToDto(E entity, Class<D> dClass) {
        Territory territory = (Territory) entity;
        TerritoryDto dto = new TerritoryDto(uuidToString(territory.getId()), territory.getNomeTerritorio(), String.valueOf(territory.getTipoTerritorio()));
        return (D) dto;
    }

    @Override
    public <E, D> E convertToEntity(D dto, Class<E> dClass) {
        TerritoryDto territoryDto = (TerritoryDto) dto;

        Territory territory = new Territory();
        territory.setId(stringToUuid(territoryDto.id()));
        territory.setNomeTerritorio(territoryDto.nomeTerritorio());
        territory.setTipoTerritorio(TerritoryType.valueOf(territoryDto.type()));

        return (E) territory;
    }
}
