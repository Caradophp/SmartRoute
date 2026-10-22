package com.faesa.smartRoute.repository;

import com.faesa.smartRoute.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

    Optional<Role> findByNomePerfil(String nomePerfil);

    @Query("FROM Role r WHERE r.nomePerfil = 'APP_USER'")
    Role findDefaultRole();
}
