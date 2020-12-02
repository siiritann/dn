package com.example.datanor.repository;

import com.example.datanor.model.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CityRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public void addCitiesToBase(long id, String name, String country, String state) {
        String sql = "INSERT INTO cities (id, name, country_code, state_code) " +
                "VALUES (:id, :name, :country, :state)";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        paramMap.put("name", name);
        paramMap.put("country", country);
        paramMap.put("state", state);
        jdbcTemplate.update(sql, paramMap);
    }

    public List<City> getCityByName(String name) {
        String name2 = name + "%";
        String sql = "SELECT * FROM cities WHERE name ILIKE :name2 ";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("name2", name2);
        List<City> resultList = jdbcTemplate.query(sql, paramMap, new CityRowMapper());
        return resultList;
    }

    public void addTrackedCity(long id) {
        String sql = "INSERT INTO tracked_cities (id) VALUES (:id)";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        jdbcTemplate.update(sql, paramMap);
    }


    public List<City> getMyCities() {
        String sql = "SELECT * FROM tracked_cities t JOIN cities c ON (t.id = c.id)";
        Map<String, Object> paramMap = new HashMap<>();
        List<City> weatherList = jdbcTemplate.query(sql, paramMap, new CityRowMapper());
        return weatherList;
    }

    public List<Long> getMyCitiesIds() {
        String sql = "SELECT id FROM tracked_cities";
        Map<String, Object> paramMap = new HashMap<>();
        List<Long> idList = jdbcTemplate.queryForList(sql, paramMap, Long.class);
        return idList;
    }

    public int deleteCity(long id) {
        String sql = "DELETE FROM tracked_cities WHERE id = :id";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        return jdbcTemplate.update(sql, paramMap);
    }

    public String getCityNameById(long id) {
        String sql = "SELECT name FROM cities WHERE id = :id";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        return jdbcTemplate.queryForObject(sql, paramMap, String.class);
    }


    public int countBaseCities() {
        String sql = "SELECT COUNT(*) FROM cities";
        return jdbcTemplate.queryForObject(sql, new HashMap<>(), Integer.class);
    }
}
