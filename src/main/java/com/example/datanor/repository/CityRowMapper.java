package com.example.datanor.repository;

import com.example.datanor.model.City;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CityRowMapper implements RowMapper<City> {
    @Override
    public City mapRow(ResultSet resultSet, int i) throws SQLException {
        City city = new City();
        city.setId(resultSet.getLong("id"));
        city.setName(resultSet.getString("name"));
        city.setCountryCode(resultSet.getString("country_code"));
        city.setStateCode(resultSet.getString("state_code"));
        return city;
    }
}
