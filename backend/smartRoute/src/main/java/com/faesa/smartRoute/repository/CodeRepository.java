package com.faesa.smartRoute.repository;

import com.faesa.smartRoute.model.Code;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CodeRepository extends JpaRepository<Code, UUID> {

    Optional<Code> findByCodeSendedAndUserEmailAndCreatedAtBetween(Integer code, String email, LocalDateTime start, LocalDateTime end);

}
