package com.example.datanor.repository;

import com.example.datanor.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AppUser, Long> {

    AppUser getUserByUsername(String username);

}

