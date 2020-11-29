package com.example.datanor.repository;

import com.example.datanor.controller.City;
import com.example.datanor.controller.CityRowMapper;
import com.example.datanor.controller.CityWeatherRowMapper;
import com.example.datanor.controller.CityWeather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class WeatherRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    // TODO ADD READ FROM JSON HERE
    public List<City> getCityInfoById(long id) {
        String sql = "SELECT * FROM cities where id = :id";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        List<City> resultList = jdbcTemplate.query(sql, paramMap, new CityRowMapper());
        return resultList;
    }

    // TODO
    public String addCity(long id, String name, String country) {
        String sql = "INSERT INTO cities (id, name, country_code) VALUES (:id, :name, :country)";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        paramMap.put("name", name);
        paramMap.put("country", country);
        jdbcTemplate.update(sql, paramMap);
        return "City " + name + " added to your poll list";
    }


    public void addCityWeather(CityWeather cityWeather){
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

    public List getAllWeatherData() {
        String sql = "SELECT * FROM weather";
        Map<String, Object> paramMap = new HashMap<>();
        List<CityWeather> weatherList = jdbcTemplate.query(sql, paramMap, new CityWeatherRowMapper());
        return weatherList;
    }

    public List getMyCities() {
        String sql = "SELECT * FROM cities";
        Map<String, Object> paramMap = new HashMap<>();
        List<City> weatherList = jdbcTemplate.query(sql, paramMap, new CityRowMapper());
        return weatherList;
    }

    public List getWeatherForOneCity(long id){
        String sql = "SELECT * FROM weather where city_id = :cityId";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("cityId", id);
        List<CityWeather> resultList = jdbcTemplate.query(sql, paramMap, new CityWeatherRowMapper());
        return resultList;

    }

    public int deleteCity(long id){
        String sql = "DELETE FROM cities where id = :id";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        return jdbcTemplate.update(sql, paramMap);
    }

    public List<City> getCityByName(String name) {
        String sql = "SELECT * FROM cities where name = :name";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        List<City> resultList = jdbcTemplate.query(sql, paramMap, new CityRowMapper());
        return resultList;
    }
}

