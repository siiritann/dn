package com.example.datanor.repository;

import com.example.datanor.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<AppUser, Long> {

    AppUser getUserByUsername(String username);

    @Query("SELECT id FROM AppUser " +
            "where username = :username")
    Long getIdByUsername(@Param("username") String username);

}

