package com.faesa.smartRoute.service;

import com.faesa.smartRoute.dto.HealthUnitDto;
import com.faesa.smartRoute.dto.RouteDto;
import com.faesa.smartRoute.dto.response.NearesHealthPointResponseDto;
import com.faesa.smartRoute.dto.response.OsrmRouteResponseDto;
import com.faesa.smartRoute.exceptions.RecordNotFoundException;
import com.faesa.smartRoute.model.HealthUnit;
import com.faesa.smartRoute.model.enums.HealthUnitType;
import com.faesa.smartRoute.repository.HealthUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class HealthUnitService extends BaseService {

    @Autowired
    private HealthUnitRepository healthUnitRepository;

    @Autowired
    private DistanceCalculatorService distanceCalculatorService;

    @Autowired
    private RoutingService routingService;

    public List<HealthUnitDto> findAll() {
        return healthUnitRepository.findAll()
                .stream().map(healthUnit -> convertToDto(healthUnit, HealthUnitDto.class))
                .toList();
    }

    public void save(HealthUnitDto healthUnitDto) {
        HealthUnit healthUnit = this.convertToEntity(healthUnitDto, HealthUnit.class);
        healthUnitRepository.save(healthUnit);
    }

    @Transactional
    public void update(String id, HealthUnitDto healthUnitDto) {
        HealthUnit healthUnit = convertToEntity(healthUnitDto, HealthUnit.class);

        UUID uuid = stringToUuid(id);
        if (!healthUnitRepository.existsById(uuid)) {
            throw new RecordNotFoundException("Unidade de saúde não encontrada");
        }

        healthUnit.setId(uuid);
        healthUnitRepository.save(healthUnit);
    }

    public void delete(String id) {
        UUID uuid = stringToUuid(id);

        Optional<HealthUnit> healthUnitOptional = healthUnitRepository.findById(uuid);

        if (healthUnitOptional.isEmpty()) {
            throw new RecordNotFoundException("Unidade de saúde não encontrada");
        }

        healthUnitRepository.delete(healthUnitOptional.get());
    }


    public NearesHealthPointResponseDto findNearesHealthPoint(double userLatitude, double userLongitude) {
        List<HealthUnit> healthUnits = healthUnitRepository.findAll();

        if (healthUnits.isEmpty()) {
            throw new RecordNotFoundException("Nunhuma unidade de saúde encontrada");
        }

        List<HealthUnit> candidates = healthUnits.stream()
                .filter(unit -> unit.getLongitude() != null && unit.getLatitude() != null)
                .sorted(Comparator.comparing(unit -> distanceCalculatorService.calculateDistance(
                        new BigDecimal(userLatitude),
                        new BigDecimal(userLongitude),
                        unit.getLatitude(),
                        unit.getLongitude()
                )))
                .limit(5)
                .toList();

        if (candidates.isEmpty()) {
            throw new RecordNotFoundException("Nenhuma unidade de saúde perto de você.");
        }

        final AtomicReference<NearesHealthPointResponseDto>[] bestResponse = new AtomicReference[]{new AtomicReference<>()};
        final double[] shortestRouteDistance = {Double.MAX_VALUE};

        candidates.forEach(candidate -> {
            try {

                OsrmRouteResponseDto.OsrmRoute route = routingService.calculateRoute(
                        userLatitude,
                        userLongitude,
                        candidate.getLatitude().doubleValue(),
                        candidate.getLongitude().doubleValue()
                );

                if (route.distance() < shortestRouteDistance[0]) {
                    shortestRouteDistance[0] = route.distance();

                    HealthUnitDto healthPointDTO =
                            new HealthUnitDto(
                                    String.valueOf(candidate.getId()),
                                    candidate.getNome(),
                                    candidate.getTipo().toString(),
                                    candidate.getLatitude().doubleValue(),
                                    candidate.getLongitude().doubleValue()
                            );

                    RouteDto routeDTO = new RouteDto(
                            route.distance(),
                            route.duration(),
                            route.geometry().coordinates()
                    );

                    bestResponse[0].set(new NearesHealthPointResponseDto(
                            healthPointDTO,
                            routeDTO
                    ));
                }

            } catch (Exception e) {
                throw e;
            }
        });

        if (Objects.isNull(bestResponse[0])) {
            throw new RecordNotFoundException("Não foi possivel uma oa opção para sua posição");
        }

        return bestResponse[0].get();
    }

    @Override
    public <E, D> D convertToDto(E entity, Class<D> dClass) {
        HealthUnit healthUnit = (HealthUnit) entity;

        HealthUnitDto healthUnitDto
                = new HealthUnitDto(
                        String.valueOf(healthUnit.getId()),
                        healthUnit.getNome(),
                        healthUnit.getTipo().name(),
                healthUnit.getLatitude().doubleValue(),
                healthUnit.getLongitude().doubleValue());

        return (D) healthUnitDto;
    }

    @Override
    public <E, D> E convertToEntity(D dto, Class<E> dClass) {

        HealthUnitDto healthUnitDto = (HealthUnitDto) dto;
        HealthUnit healthUnit = new HealthUnit();

        if (healthUnitDto.id() != null && !healthUnitDto.id().isBlank()) {
            healthUnit.setId(stringToUuid(healthUnitDto.id()));
        }
        healthUnit.setNome(healthUnitDto.name());
        healthUnit.setTipo(HealthUnitType.valueOf(healthUnitDto.tipo()));
        healthUnit.setLongitude(BigDecimal.valueOf(healthUnitDto.longitude()));
        healthUnit.setLatitude(BigDecimal.valueOf(healthUnitDto.latitude()));

        return (E) healthUnit;
    }
}
