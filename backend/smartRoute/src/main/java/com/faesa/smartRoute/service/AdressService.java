package com.faesa.smartRoute.service;

import com.faesa.smartRoute.dto.request.AdressRequestDto;
import com.faesa.smartRoute.dto.response.AdressResponseDto;
import com.faesa.smartRoute.exceptions.BusinessException;
import com.faesa.smartRoute.exceptions.RecordNotFoundException;
import com.faesa.smartRoute.model.Adress;
import com.faesa.smartRoute.model.Territory;
import com.faesa.smartRoute.repository.AdressRepository;
import com.faesa.smartRoute.repository.TerritoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class AdressService extends BaseService {

    @Autowired
    private AdressRepository repository;

    @Autowired
    private TerritoryRepository territoryRepository;

    public List<AdressResponseDto> findAll() {
        return repository.findAll()
                .stream().map(adress -> convertToDto(adress, AdressResponseDto.class))
                .toList();
    }

    public AdressResponseDto findById(String id) {
        return repository.findById(UUID.fromString(id))
                .map(adress -> convertToDto(adress, AdressResponseDto.class))
                .orElseThrow(() -> new RecordNotFoundException("Adress não encontrado"));
    }

    public void save(AdressRequestDto dto) {
        Adress entity = convertToEntity(dto, Adress.class);
        repository.save(entity);
    }

    @Transactional
    public void update(String id, AdressRequestDto dto) {
        UUID uuid = UUID.fromString(id);
        if (!repository.existsById(uuid)) {
            throw new RecordNotFoundException("Adress não encontrado");
        }
        Adress entity = convertToEntity(dto, Adress.class);
        entity.setId(uuid);
        repository.save(entity);
    }

    @Transactional
    public void delete(String id) {
        UUID uuid = UUID.fromString(id);
        if (!repository.existsById(uuid)) {
            throw new RecordNotFoundException("Adress não encontrado");
        }
        repository.deleteById(uuid);
    }

    @Override
    public <E, D> D convertToDto(E entity, Class<D> dClass) {
        Adress adress = (Adress) entity;

        AdressResponseDto adressResponseDto
                = new AdressResponseDto(uuidToString(adress.getId()), adress.getRua(), adress.getNumero(), adress.getBairro(), adress.getCidade(), adress.getTerritorio().getNomeTerritorio());

        return (D) adressResponseDto;
    }

    @Override
    public <E, D> E convertToEntity(D dto, Class<E> dClass) {
        AdressRequestDto adressRequestDto = (AdressRequestDto) dto;
        Adress adress = new Adress();
        adress.setId(stringToUuid(adressRequestDto.id()));
        adress.setRua(adressRequestDto.rua());
        adress.setNumero(adressRequestDto.numero());
        adress.setBairro(adressRequestDto.bairro());
        adress.setCidade(adressRequestDto.cidade());

        Territory territory = territoryRepository
                .findById(stringToUuid(adressRequestDto.territoryId())).orElseThrow(() -> new BusinessException("Território não encontrado"));

        adress.setTerritorio(territory);

        return (E) adress;
    }
}
