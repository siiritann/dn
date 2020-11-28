package com.example.datanor.controller;

import com.example.datanor.controller.CityWeather;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CityWeatherRowMapper implements RowMapper {
    @Override
    public CityWeather mapRow(ResultSet resultSet, int i) throws SQLException {
        CityWeather cityWeather = new CityWeather();
        cityWeather.setId(resultSet.getLong("id"));
        cityWeather.setCityId(resultSet.getLong("city_id"));
        cityWeather.setTemperatureInCelsius(resultSet.getBigDecimal("temp_celsius"));
        cityWeather.setWindSpeed(resultSet.getDouble("wind_speed"));
        cityWeather.setHumidity(resultSet.getInt("humidity"));
        cityWeather.setTimestamp(resultSet.getTimestamp("timestamp"));
        return cityWeather;
    }
}
