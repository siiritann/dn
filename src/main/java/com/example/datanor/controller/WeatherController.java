package com.example.datanor.controller;

import com.example.datanor.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
public class WeatherController {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private WeatherService weatherService;


    @PostMapping("/updateuri/{id}")
    public URI updateUri(@PathVariable("id") long id) throws URISyntaxException {
        return weatherService.appendUri(id);
    }

    @PostMapping("/weather/add/{id}")
    public void addCityWeather(@PathVariable("id") long id) throws Exception {
        weatherService.addCityWeather(id);
    }

    @GetMapping("/weather/all")
    public List getAllWeatherData() {
        return weatherService.getAllWeatherData();
    }

    @GetMapping("/weather/{id}")
    public List getWeatherForOneCity(@PathVariable("id") long id){
        return weatherService.getWeatherForOneCity(id);
    }



}
