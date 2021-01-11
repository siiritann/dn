package com.example.datanor.repository;

import com.example.datanor.model.CityWeather;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;


@Deprecated(since = "2021-01-11", forRemoval = true)
public class CityWeatherRowMapper implements RowMapper<CityWeather> {
    @Override
    public CityWeather mapRow(ResultSet resultSet, int i) throws SQLException {
        CityWeather cityWeather = new CityWeather();
        cityWeather.setId(resultSet.getLong("id"));
        cityWeather.setCityId(resultSet.getLong("city_id"));
        cityWeather.setTemperatureInCelsius(resultSet.getBigDecimal("temp_celsius"));
        cityWeather.setWindSpeed(resultSet.getBigDecimal("wind_speed"));
        cityWeather.setHumidity(resultSet.getInt("humidity"));
        cityWeather.setTimestamp(resultSet.getTimestamp("timestamp"));
        return cityWeather;
    }
}
