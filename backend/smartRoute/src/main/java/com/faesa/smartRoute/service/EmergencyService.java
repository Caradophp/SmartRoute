package com.faesa.smartRoute.service;

import com.faesa.smartRoute.dto.EmergencyDispachDto;
import com.faesa.smartRoute.dto.EmergencyDto;
import com.faesa.smartRoute.model.AppUser;
import com.faesa.smartRoute.model.Emergency;
import com.faesa.smartRoute.repository.EmergencyRepository;
import com.faesa.smartRoute.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class EmergencyService {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private EmergencyRepository emergencyRepository;

    public void notificarChamadaEmergencia(EmergencyDto emergencyDto) {
        AppUser currentUser = (AppUser) Util.getCurrentUser();
        EmergencyDispachDto emergencyDispachDto = new EmergencyDispachDto(
                emergencyDto.latitude(),
                emergencyDto.longitude(),
                currentUser.getNome(),
                currentUser.getEmail(),
                currentUser.getTelefone());
        simpMessagingTemplate.convertAndSend("/topic/emergencias", emergencyDispachDto);

        Emergency emergency = new Emergency();
        emergency.setLatitude(emergencyDto.latitude());
        emergency.setLongitude(emergencyDto.longitude());
        emergency.setAppUser(currentUser);

        emergencyRepository.save(emergency);
    }

}
