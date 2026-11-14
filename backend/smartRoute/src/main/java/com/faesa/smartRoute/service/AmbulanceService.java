package com.faesa.smartRoute.service;

import com.faesa.smartRoute.dto.AmbulanceDto;
import com.faesa.smartRoute.exceptions.RecordNotFoundException;
import com.faesa.smartRoute.model.Ambulance;
import com.faesa.smartRoute.model.Driver;
import com.faesa.smartRoute.model.HealthAgent;
import com.faesa.smartRoute.model.enums.AmbulanceStaus;
import com.faesa.smartRoute.repository.AmbulanceRepository;
import com.faesa.smartRoute.repository.DriverRepository;
import com.faesa.smartRoute.repository.HealthAgentRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AmbulanceService extends BaseService {

    @Autowired
    private AmbulanceRepository ambulanceRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private HealthAgentRepository healthAgentRepository;

    public Ambulance callAmbulande() {
        return ambulanceRepository.findAll((root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("status"), AmbulanceStaus.WAITING));
            return cb.and(predicates);
        }).stream().findFirst().orElseThrow(() -> new RecordNotFoundException("Sem ambulâncias disponiveis no momento.", true));
    }

    public void save(AmbulanceDto ambulanceDto) {
        Ambulance ambulance = convertToEntity(ambulanceDto, Ambulance.class);
        ambulanceRepository.save(ambulance);
    }

    @Transactional
    public void update(String id, AmbulanceDto ambulanceDto) {
        Ambulance ambulance = convertToEntity(ambulanceDto, Ambulance.class);

        UUID uuid = stringToUuid(id);
        if (!ambulanceRepository.existsById(uuid)) {
            throw new RecordNotFoundException("Ambulância não encontrada");
        }

        ambulance.setId(uuid);
        ambulanceRepository.save(ambulance);
    }

    public void delete(String id) {
        Optional<Ambulance> ambulanceOptional = ambulanceRepository.findById(stringToUuid(id));

        if (ambulanceOptional.isEmpty()) {
            throw new RecordNotFoundException("Ambulância não encontrada");
        }

        ambulanceRepository.delete(ambulanceOptional.get());
    }

    @Override
    public <E, D> D convertToDto(E entity, Class<D> dClass) {
        Ambulance ambulance = (Ambulance) entity;
        AmbulanceDto dto
                = new AmbulanceDto(
                        ambulance.getPlaca(),
                        ambulance.getStatus(),
                        String.valueOf(ambulance.getDriver().getId()),
                        String.valueOf(ambulance.getHealthAgent().getId()));

        return (D) dto;
    }

    @Override
    public <E, D> E convertToEntity(D dto, Class<E> dClass) {
        AmbulanceDto ambulanceDto = (AmbulanceDto) dto;

        Ambulance ambulance = new Ambulance();

        Driver driver = driverRepository.findById(UUID.fromString(ambulanceDto.driverId())).orElseThrow();
        HealthAgent healthAgent = healthAgentRepository.findById(UUID.fromString(ambulanceDto.healthAgentId())).orElseThrow();

        ambulance.setPlaca(ambulanceDto.placa());
        ambulance.setStatus(ambulanceDto.status());
        ambulance.setDriver(driver);
        ambulance.setHealthAgent(healthAgent);

        return (E) ambulance;
    }
}
