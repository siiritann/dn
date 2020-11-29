package com.example.datanor.repository;

import com.example.datanor.controller.City;
import com.example.datanor.controller.CityRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CityRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public void addTrackedCity(long id){
        String sql = "INSERT INTO tracked_cities (id) VALUES (:id)";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        jdbcTemplate.update(sql, paramMap);
    }

    public List getAllCities() {
        String sql = "SELECT * FROM cities";
        Map<String, Object> paramMap = new HashMap<>();
        List<City> weatherList = jdbcTemplate.query(sql, paramMap, new CityRowMapper());
        return weatherList;
    }

    public List getMyCities() {
        String sql = "SELECT * FROM tracked_cities t JOIN cities c ON (t.id = c.id)";
        Map<String, Object> paramMap = new HashMap<>();
        List<City> weatherList = jdbcTemplate.query(sql, paramMap, new CityRowMapper());
        return weatherList;
    }

    public int deleteCity(long id){
        String sql = "DELETE FROM tracked_cities where id = :id";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        return jdbcTemplate.update(sql, paramMap);
    }

    // TODO DO I NEED THIS?
    public City getCityByName(String name) {
        String sql = "SELECT * FROM cities where name = :name";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        List<City> resultList = jdbcTemplate.query(sql, paramMap, new CityRowMapper());
        City city = resultList.get(0);
        return city;
    }

    // TODO ADD READ FROM JSON HERE
    public List<City> getCityInfoById(long id) {
        String sql = "SELECT * FROM cities where id = :id";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        List<City> resultList = jdbcTemplate.query(sql, paramMap, new CityRowMapper());
        return resultList;
    }

}
