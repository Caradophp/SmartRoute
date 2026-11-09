package com.faesa.smartRoute.service;

import com.faesa.smartRoute.dto.HealthUnitDto;
import com.faesa.smartRoute.dto.RouteDto;
import com.faesa.smartRoute.dto.response.NearesHealthPointResponseDto;
import com.faesa.smartRoute.dto.response.OsrmRouteResponseDto;
import com.faesa.smartRoute.exceptions.RecordNotFoundException;
import com.faesa.smartRoute.model.HealthUnit;
import com.faesa.smartRoute.repository.HealthUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class HealthUnitService extends BaseService {

    @Autowired
    private HealthUnitRepository healthUnitRepository;

    @Autowired
    private DistanceCalculatorService distanceCalculatorService;

    @Autowired
    private RoutingService routingService;

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
                e.printStackTrace();
            }
        });

        if (Objects.isNull(bestResponse[0])) {
            throw new RecordNotFoundException("Não foi possivel uma oa opção para sua posição");
        }

        return bestResponse[0].get();
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
