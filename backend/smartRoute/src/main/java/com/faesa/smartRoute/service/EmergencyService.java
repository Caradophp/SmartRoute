package com.faesa.smartRoute.service;

import com.faesa.smartRoute.dto.AmbulanceDto;
import com.faesa.smartRoute.dto.EmergencyDispachDto;
import com.faesa.smartRoute.dto.EmergencyDto;
import com.faesa.smartRoute.exceptions.RecordNotFoundException;
import com.faesa.smartRoute.model.Ambulance;
import com.faesa.smartRoute.model.AppUser;
import com.faesa.smartRoute.model.Emergency;
import com.faesa.smartRoute.repository.EmergencyRepository;
import com.faesa.smartRoute.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmergencyService {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private EmergencyRepository emergencyRepository;

    @Autowired
    private AmbulanceService ambulanceService;

    public void notificarChamadaEmergencia(EmergencyDto emergencyDto) {
        AppUser currentUser = (AppUser) Util.getCurrentUser();
        EmergencyDispachDto emergencyDispachDto = new EmergencyDispachDto(
                emergencyDto.latitude(),
                emergencyDto.longitude(),
                String.valueOf(currentUser.getId()),
                currentUser.getNome(),
                currentUser.getEmail(),
                currentUser.getTelefone());
        simpMessagingTemplate.convertAndSend("/topic/emergencias", emergencyDispachDto);

        Emergency emergency = new Emergency();
        emergency.setLatitude(emergencyDto.latitude());
        emergency.setLongitude(emergencyDto.longitude());
        emergency.setAppUser(currentUser);
        emergency.setAttended(Boolean.FALSE );

        emergencyRepository.save(emergency);
    }

    public void callAmbulanceNow(EmergencyDispachDto emergencyDispachDto) {

        List<Emergency> byLongitudeAndLatitude = emergencyRepository
                .findByLongitudeAndLatitudeAndAttendedFalse(emergencyDispachDto.longitude(), emergencyDispachDto.latitude());
        Emergency emergency = null;

        if (byLongitudeAndLatitude.isEmpty()) {
            throw new RecordNotFoundException("Nenhuma emergência encontrada no local informado");
        } else {
            emergency = byLongitudeAndLatitude.getFirst();
        }

        Ambulance ambulance = ambulanceService.callAmbulande();

        emergency.getAmbulance().add(ambulance);
        emergency.setAttended(Boolean.TRUE);
        emergencyRepository.save(emergency);

        simpMessagingTemplate.convertAndSend("/topic/ambulancias", emergencyDispachDto);
    }

    public Set<AmbulanceDto> checkIfIsNecessary(String id, Double longitude, Double latitude) {
        Set<Emergency> byAmbulanceIdAndLatitudeAndLongitudeAndAttendedFalse
                = emergencyRepository.findByAppUserIdAndLatitudeAndLongitudeAndAttendedFalse(UUID.fromString(id),longitude, latitude);

        if (byAmbulanceIdAndLatitudeAndLongitudeAndAttendedFalse.isEmpty()) {
            return Collections.emptySet();
        } else {
            return byAmbulanceIdAndLatitudeAndLongitudeAndAttendedFalse.stream()
                    .findFirst()
                    .get()
                    .getAmbulance()
                    .stream()
                    .map(a -> ambulanceService.convertToDto(a, AmbulanceDto.class))
                    .collect(Collectors.toSet());
        }
    }

}
