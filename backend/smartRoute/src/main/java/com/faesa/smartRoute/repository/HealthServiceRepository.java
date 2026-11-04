package com.faesa.smartRoute.repository;

import com.faesa.smartRoute.model.HealthService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface HealthServiceRepository extends JpaRepository<HealthService, UUID> {
}
