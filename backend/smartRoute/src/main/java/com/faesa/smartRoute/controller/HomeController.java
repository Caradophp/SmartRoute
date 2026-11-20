package com.faesa.smartRoute.controller;

import com.faesa.smartRoute.repository.EmergencyRepository;
import com.faesa.smartRoute.service.AmbulanceService;
import com.faesa.smartRoute.service.HealthAgentService;
import com.faesa.smartRoute.service.TerritoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private AmbulanceService ambulanceService;

    @Autowired
    private HealthAgentService healthAgentService;

    @Autowired
    private TerritoryService territoryService;

    @Autowired
    private EmergencyRepository emergencyRepository;

    @GetMapping
    public Map<String, Integer> findInfos() {
        Map<String, Integer> mapToReturn = new HashMap<>();
        mapToReturn.put("ambulancies", ambulanceService.findAll().size());
        mapToReturn.put("healthAgents", healthAgentService.findAll().size());
        mapToReturn.put("territories", territoryService.findAll().size());
        mapToReturn.put("emergencies", emergencyRepository.findAll().size());
        return mapToReturn;
    }

}
