package com.faesa.smartRoute.repository;

import com.faesa.smartRoute.model.Emergency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface EmergencyRepository extends JpaRepository<Emergency, UUID> {

    List<Emergency> findByLongitudeAndLatitudeAndAttendedFalse(Double longitude, Double latitude);

    Set<Emergency> findByAmbulanceIdAndLatitudeAndLongitudeAndAttendedFalse(UUID ambulanceId, Double longitude, Double latitude);

}
