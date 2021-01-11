package com.example.datanor.repository;

import com.example.datanor.model.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WeatherRepositoryHibernate extends JpaRepository<Weather, Long> {

    List<Weather> findAllByCityId(Long id);


    @Query(value = "SELECT w FROM Weather w JOIN CityEntity c ON (w.cityId = c.id) WHERE c.id = :id")
    List<Weather> getWeatherByCityId(@Param("id") Long id);

}

