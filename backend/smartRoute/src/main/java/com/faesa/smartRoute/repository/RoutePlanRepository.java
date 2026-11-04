package com.faesa.smartRoute.repository;

import com.faesa.smartRoute.model.RoutePlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoutePlanRepository extends JpaRepository<RoutePlan, UUID> {
}
