package com.example.datanor.controller;

import com.example.datanor.model.CityWeather;
import com.example.datanor.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class WeatherController {


    @Autowired
    private WeatherService weatherService;


    @PostMapping("/weather/add/{id}")
    public void addCityWeather(@PathVariable("id") long id) {
        weatherService.addCityWeather(id);
    }


    @GetMapping("/weather/{id}")
    public List<CityWeather> getWeatherForOneCity(@PathVariable("id") long id) {
        return weatherService.getWeatherForOneCity(id);
    }

}
