package com.example.datanor.repository;

import com.example.datanor.model.UserCity;
import com.example.datanor.model.UserCityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserCityRepository extends JpaRepository<UserCity, UserCityId> {

    @Query(value = "SELECT cityId FROM UserCity " +
            "WHERE userId = :userId")
    List<Long> findCityIdsByUserId(@Param("userId") Long userId);
}

