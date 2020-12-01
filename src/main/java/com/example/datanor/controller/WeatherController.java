package com.example.datanor.controller;

import com.example.datanor.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@CrossOrigin
@RestController
public class WeatherController {


    @Autowired
    private WeatherService weatherService;


    @PostMapping("/updateuri/{id}")
    public URI updateUri(@PathVariable("id") long id) throws URISyntaxException {
        return weatherService.appendUri(id);
    }

    @PostMapping("/weather/add/{id}")
    public void addCityWeather(@PathVariable("id") long id)  {
        weatherService.addCityWeather(id);
    }


    @GetMapping("/weather/{id}")
    public List<CityWeather> getWeatherForOneCity(@PathVariable("id") long id){
        return weatherService.getWeatherForOneCity(id);
    }

}
