package com.faesa.smartRoute.repository;

import com.faesa.smartRoute.model.Ambulance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AmbulanceRepository extends JpaRepository<Ambulance, UUID> {

    Optional<Ambulance> findByPlaca(String placa);

}
