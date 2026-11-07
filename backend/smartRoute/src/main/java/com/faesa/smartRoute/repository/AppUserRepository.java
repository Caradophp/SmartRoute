package com.faesa.smartRoute.repository;

import com.faesa.smartRoute.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppUserRepository extends UserRepository {

    @Override
    @Query("FROM User u WHERE u.id IN (SELECT a.id FROM AppUser a)")
    List<User> findAll();
}
