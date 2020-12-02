package com.example.datanor.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class CityWeather {
    long id;
    long cityId;
    BigDecimal temperatureInCelsius;
    BigDecimal windSpeed;
    int humidity;
    Timestamp timestamp = new java.sql.Timestamp(System.currentTimeMillis());

    public CityWeather() {
    }

    public CityWeather(long cityId, BigDecimal temperatureInCelsius, BigDecimal windSpeed, int humidity) {
        this.cityId = cityId;
        this.temperatureInCelsius = temperatureInCelsius;
        this.windSpeed = windSpeed;
        this.humidity = humidity;
        this.timestamp = new java.sql.Timestamp(System.currentTimeMillis());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCityId() {
        return cityId;
    }

    public void setCityId(long cityId) {
        this.cityId = cityId;
    }

    public BigDecimal getTemperatureInCelsius() {
        return temperatureInCelsius;
    }

    public void setTemperatureInCelsius(BigDecimal temperatureInCelsius) {
        this.temperatureInCelsius = temperatureInCelsius;
    }

    public BigDecimal getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(BigDecimal windSpeed) {
        this.windSpeed = windSpeed;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
