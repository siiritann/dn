package com.example.datanor.controller;

import com.example.datanor.model.Weather;
import com.example.datanor.service.WeatherService;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@CrossOrigin
@RestController
public class WeatherController {


    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @PostMapping("/weather/add/{id}")
    public void addCityWeather(@PathVariable("id") long id) {
        weatherService.addCityWeather(id);
    }


    @GetMapping("/weather/{id}")
    public List<Weather> getWeatherForOneCity(@PathVariable("id") long id) {
        return weatherService.getWeatherForOneCity(id);
    }

    // TESTING ONLY
    @GetMapping("/url/{id}")
    public URI geturl(@PathVariable("id") long id) throws URISyntaxException {
        return weatherService.getUriForCity(id);
    }
}
