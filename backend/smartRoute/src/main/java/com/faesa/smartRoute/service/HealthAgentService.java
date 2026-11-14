package com.faesa.smartRoute.service;

import com.faesa.smartRoute.dto.HealthUnitDto;
import com.faesa.smartRoute.dto.request.HealthAgentRequestDto;
import com.faesa.smartRoute.exceptions.RecordNotFoundException;
import com.faesa.smartRoute.model.HealthAgent;
import com.faesa.smartRoute.model.User;
import com.faesa.smartRoute.repository.HealthAgentRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class HealthAgentService extends BaseService {


    @Autowired
    private HealthAgentRepository healthAgentRepository;

    @Autowired
    private UserService userService;

    public void save(HealthAgentRequestDto healthAgentRequestDto) {
        String telefone = healthAgentRequestDto.telefone();
        UUID userId = UUID.fromString(healthAgentRequestDto.userId());
        User user = userService.findById(userId);

        // Sanitiza telefone removendo máscara
        telefone = StringUtils
                .replaceChars(telefone, "[-()]", "")
                .trim();

        HealthAgent healthAgent = new HealthAgent();
        healthAgent.setTelefone(telefone);
        healthAgent.setUser(user);

        healthAgentRepository.save(healthAgent);
    }

    @Transactional
    public void update(String id, HealthAgentRequestDto healthAgentRequestDto) {
        HealthAgent healthAgent = convertToEntity(healthAgentRequestDto, HealthAgent.class);

        UUID uuid = stringToUuid(id);
        if (healthAgentRepository.existsById(uuid)) {
            throw new RecordNotFoundException("Agênte de saúde não encontrado");
        }

        healthAgent.setId(uuid);
        healthAgent.setTelefone(StringUtils
                .replaceChars(healthAgent.getTelefone(), "[-()]", "")
                .trim());
        healthAgentRepository.save(healthAgent);
    }

    public void delede(String id) {
        Optional<HealthAgent> healthAgentOptional = healthAgentRepository.findById(stringToUuid(id));

        if (healthAgentOptional.isEmpty()) {
            throw new RecordNotFoundException("Agente de saúde não encontrado");
        }

        healthAgentRepository.delete(healthAgentOptional.get());
    }

    @Override
    public <E, D> D convertToDto(E entity, Class<D> dClass) {
        return null;
    }

    @Override
    public <E, D> E convertToEntity(D dto, Class<E> dClass) {
        HealthAgentRequestDto healthAgentRequestDto = (HealthAgentRequestDto) dto;

        HealthAgent healthAgent = new HealthAgent();

        User user = userService.findById(stringToUuid(healthAgentRequestDto.userId()));

        healthAgent.setUser(user);
        healthAgent.setTelefone(healthAgentRequestDto.telefone());

        return (E) healthAgent;
    }
}
