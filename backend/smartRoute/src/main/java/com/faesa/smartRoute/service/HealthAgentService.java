package com.faesa.smartRoute.service;

import com.faesa.smartRoute.dto.request.HealthAgentRequestDto;
import com.faesa.smartRoute.model.HealthAgent;
import com.faesa.smartRoute.model.User;
import com.faesa.smartRoute.repository.HealthAgentRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class HealthAgentService extends BaseService {


    @Autowired
    private HealthAgentRepository healthAgentRepository;

    @Autowired
    private UserService userService;

    public void saveAgent(HealthAgentRequestDto healthAgentRequestDto) {
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


    @Override
    public <E, D> D convertToDto(E entity, Class<D> dClass) {
        return null;
    }

    @Override
    public <E, D> E convertToEntity(D dto, Class<E> dClass) {
        return null;
    }
}
