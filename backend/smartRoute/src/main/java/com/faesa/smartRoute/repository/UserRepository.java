package com.faesa.smartRoute.repository;

import com.faesa.smartRoute.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("FROM User u JOIN AppUser a ON a.id = u.id WHERE u.nome LIKE %:param% OR u.email LIKE %:param%")
    List<User> search(@Param("param") String param);

}
