package com.faesa.smartRoute.repository;

import com.faesa.smartRoute.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DriverRepository extends JpaRepository<Driver, UUID> {

    Optional<Driver> findByDriverNome(String nome);

    List<Driver> findByDriverLicenceLevel(Character level);

}
