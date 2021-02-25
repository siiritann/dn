package com.example.datanor.repository;

import com.example.datanor.model.City;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CityRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CityRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

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
        String sql = "SELECT * FROM cities WHERE name ILIKE :name";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", "%"+name+"%");
        List<City> resultList = jdbcTemplate.query(sql, paramMap, new CityRowMapper());
        return resultList;
    }

    @Deprecated
    public void addTrackedCity(long id) {
        String sql = "INSERT INTO tracked_cities (id) VALUES (:id)";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        jdbcTemplate.update(sql, paramMap);
    }

    @Deprecated
    public List<City> getMyCities() {
        String sql = "SELECT * FROM tracked_cities t JOIN cities c ON (t.id = c.id)";
        Map<String, Object> paramMap = new HashMap<>();
        List<City> weatherList = jdbcTemplate.query(sql, paramMap, new CityRowMapper());
        return weatherList;
    }

    public List<City> getMyCitiesRefactored(Long userId) {
        String sql = "SELECT * FROM user_cities t " +
                "JOIN cities c ON (t.city_id = c.id) " +
                "WHERE user_id = :userId";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        List<City> weatherList = jdbcTemplate.query(sql, paramMap, new CityRowMapper());
        return weatherList;
    }

    public List<Long> getMyCitiesIds() {
        String sql = "SELECT city_id FROM user_cities";
        Map<String, Object> paramMap = new HashMap<>();
        List<Long> idList = jdbcTemplate.queryForList(sql, paramMap, Long.class);
        return idList;
    }

    @Deprecated
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
