package com.faesa.smartRoute.service;

import com.faesa.smartRoute.dto.AssistedPersonDto;
import com.faesa.smartRoute.exceptions.RecordNotFoundException;
import com.faesa.smartRoute.model.Adress;
import com.faesa.smartRoute.model.AssistedPerson;
import com.faesa.smartRoute.model.Territory;
import com.faesa.smartRoute.model.VulnerabilityType;
import com.faesa.smartRoute.repository.AdressRepository;
import com.faesa.smartRoute.repository.AssistedPersonRepository;
import com.faesa.smartRoute.repository.TerritoryRepository;
import com.faesa.smartRoute.repository.VulnerabilityTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AssistedPersonService extends BaseService {

    @Autowired
    private AssistedPersonRepository repository;

    @Autowired
    private AdressRepository adressRepository;

    @Autowired
    private VulnerabilityTypeRepository vulnerabilityTypeRepository;
    @Autowired
    private TerritoryRepository territoryRepository;

    public List<AssistedPersonDto> findAll() {
        return repository.findAll()
                .stream().map(assistedPerson -> convertToDto(assistedPerson, AssistedPersonDto.class))
                .toList();
    }

    public AssistedPersonDto findById(String id) {
        return repository.findById(UUID.fromString(id))
                .map(assistedPerson -> convertToDto(assistedPerson, AssistedPersonDto.class))
                .orElseThrow(() -> new RecordNotFoundException("Pessoa assistida não encontrado"));
    }

    public void save(AssistedPersonDto dto) {
        repository.register(
                dto.nome(),
                dto.telefone(),
                dto.dataNascimento(),
                dto.rua(),
                dto.numero(),
                dto.bairro(),
                dto.cidade(),
                stringToUuid(dto.idTerritorio()),
                stringToUuid(dto.vulnerabilityId())
        );
    }

    @Transactional
    public void update(String id, AssistedPersonDto dto) {

        if (!repository.existsById(stringToUuid(id))) {
            throw new RecordNotFoundException();
        }

        AssistedPerson assistedPerson = convertToEntity(dto, AssistedPerson.class);
        assistedPerson.setId(stringToUuid(id));

        Adress adress = assistedPerson.getAdress();
        adress.setRua(dto.rua());
        adress.setNumero(dto.numero());
        adress.setBairro(dto.bairro());
        adress.setCidade(dto.cidade());

        Optional<Territory> territoryOptional = territoryRepository.findById(stringToUuid(dto.idTerritorio()));

        if (territoryOptional.isEmpty()) {
            throw new RecordNotFoundException();
        }

        adress.setTerritorio(territoryOptional.get());

        adressRepository.save(adress);
        repository.save(assistedPerson);
    }

    @Transactional
    public void delete(String id) {
        UUID uuid = UUID.fromString(id);
        if (!repository.existsById(uuid)) {
            throw new RecordNotFoundException("Pessoa assistida não encontrado");
        }
        repository.deleteById(uuid);
    }

    @Override
    public <E, D> D convertToDto(E entity, Class<D> dClass) {
        AssistedPerson assistedPerson = (AssistedPerson) entity;
        Adress adress = assistedPerson.getAdress();

        AssistedPersonDto assistedPersonDto
                = new AssistedPersonDto(
                        uuidToString(assistedPerson.getId()),
                        assistedPerson.getNome(),
                        assistedPerson.getDataNascimento(),
                        assistedPerson.getTelefone(),
                        adress.getRua(),
                        adress.getNumero(),
                        adress.getBairro(),
                        adress.getCidade(),
                        uuidToString(adress.getTerritorio().getId()),
                        uuidToString(assistedPerson.getVulnerabilityType().getId())
                );

        return (D) assistedPersonDto;
    }

    @Override
    public <E, D> E convertToEntity(D dto, Class<E> dClass) {
        AssistedPersonDto assistedPersonDto = (AssistedPersonDto) dto;
        Optional<Adress> byNumero = adressRepository.findByNumero(assistedPersonDto.numero());
        Optional<VulnerabilityType> vulnerabilityTypeOptional = vulnerabilityTypeRepository.findById(stringToUuid(assistedPersonDto.vulnerabilityId()));

        if (byNumero.isEmpty()) {
            throw new RecordNotFoundException("Endereço não encontrado");
        }

        Adress adress = byNumero.get();

        if (vulnerabilityTypeOptional.isEmpty()) {
            throw new RecordNotFoundException("Tipo de vulnerabilidade não encontrada");
        }

        VulnerabilityType vulnerabilityType = vulnerabilityTypeOptional.get();

        AssistedPerson assistedPerson = new AssistedPerson();
        assistedPerson.setAdress(adress);
        assistedPerson.setVulnerabilityType(vulnerabilityType);
        assistedPerson.setNome(assistedPersonDto.nome());
        assistedPerson.setTelefone(assistedPersonDto.telefone());
        assistedPerson.setDataNascimento(assistedPersonDto.dataNascimento());

        return (E) assistedPerson;
    }
}
