package com.example.datanor.repository;

import com.example.datanor.model.CityWeather;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
* Deprecated due to addition of Hibernate.
* Use WeatherRepositoryHibernate instead.
* */
@Deprecated(since = "2021-01-11", forRemoval = true)
@Repository
public class WeatherRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public WeatherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addCityWeather(CityWeather cityWeather) {
        String sql = "INSERT INTO weather (city_id, temp_celsius, wind_speed, humidity, timestamp)" +
                " VALUES (:cityId, :temperatureInCelsius, :windSpeed, :humidity, :timestamp)";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("cityId", cityWeather.getCityId());
        paramMap.put("temperatureInCelsius", cityWeather.getTemperatureInCelsius());
        paramMap.put("windSpeed", cityWeather.getWindSpeed());
        paramMap.put("humidity", cityWeather.getHumidity());
        paramMap.put("timestamp", cityWeather.getTimestamp());
        jdbcTemplate.update(sql, paramMap);
    }


    public List<CityWeather> getWeatherForOneCity(long id) {
        String sql = "SELECT * FROM weather where city_id = :cityId";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("cityId", id);
        List<CityWeather> resultList = jdbcTemplate.query(sql, paramMap, new CityWeatherRowMapper());
        return resultList;

    }


}

