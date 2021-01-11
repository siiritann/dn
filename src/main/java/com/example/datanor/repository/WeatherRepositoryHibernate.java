package com.example.datanor.repository;

import com.example.datanor.model.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WeatherRepositoryHibernate extends JpaRepository<Weather, Long> {

    List<Weather> findAllByCityId(Long id);

}

