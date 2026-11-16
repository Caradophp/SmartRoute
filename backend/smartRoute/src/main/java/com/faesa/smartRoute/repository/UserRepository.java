package com.faesa.smartRoute.repository;

import com.faesa.smartRoute.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {

    /***
     * Necessário para listar apenas usuários do sistema web, uso principalmente em dropdowns
     * Se precisar listar os usuários do Aplicativo Mobile
     * @see AppUserRepository
     */
    @Override
    @Query("FROM User u WHERE u.id NOT IN (SELECT a.id FROM AppUser a)")
    List<User> findAll();

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("FROM User u WHERE (u.nome LIKE %:param% OR u.email LIKE %:param%) AND u.id NOT IN (SELECT a.id FROM AppUser a)")
    List<User> search(@Param("param") String param);

}
