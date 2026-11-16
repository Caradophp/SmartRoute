package com.faesa.smartRoute.repository;

import com.faesa.smartRoute.model.HealthUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface HealthUnitRepository extends JpaRepository<HealthUnit, UUID> {
}
