package com.example.datanor.repository;

import com.example.datanor.model.City;
import com.example.datanor.model.UserCity;
import com.example.datanor.model.UserCityId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCityRepository extends JpaRepository<UserCity, UserCityId> {

    List<City> findAllByUserId(long l);
}

