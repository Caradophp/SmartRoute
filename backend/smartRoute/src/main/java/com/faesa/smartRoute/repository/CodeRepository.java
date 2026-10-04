package com.faesa.smartRoute.repository;

import com.faesa.smartRoute.model.Code;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface CodeRepository extends JpaRepository<Code, UUID> {

    Optional<Code> findByCodeSendedAndUserEmailAndCreatedAtBetween(Integer code, String email, LocalDateTime start, LocalDateTime end);

}
