package com.faesa.smartRoute.repository;

import com.faesa.smartRoute.model.AssistedPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface AssistedPersonRepository extends JpaRepository<AssistedPerson, UUID> {

    @NativeQuery("select func_register_assited_person(?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9)")
    String register(
            String nome,
            String telefone,
            LocalDate dataNascimento,
            String rua,
            Integer numero,
            String bairro,
            String cidade,
            UUID territoryId,
            UUID vulnerabilityId
    );

}
