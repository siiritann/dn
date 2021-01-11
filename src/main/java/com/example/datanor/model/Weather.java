package com.example.datanor.model;


import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long cityId;
    @Column(name = "temp_celsius")
    private BigDecimal temperatureInCelsius;
    private BigDecimal windSpeed;
    private int humidity;
    private Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    public Weather() {
    }

    public Weather(Long cityId, BigDecimal temperatureInCelsius, BigDecimal windSpeed, int humidity) {
        this.cityId = cityId;
        this.temperatureInCelsius = temperatureInCelsius;
        this.windSpeed = windSpeed;
        this.humidity = humidity;
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    public Weather(Long id) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
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
