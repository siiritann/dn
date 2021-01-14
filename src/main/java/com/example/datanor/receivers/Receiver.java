package com.example.datanor.receivers;

import com.example.datanor.service.CityService;
import com.example.datanor.service.WeatherService;
import org.springframework.stereotype.Component;

@Component
public class Receiver {


    WeatherService weatherService;

    CityService cityService;

    public Receiver(WeatherService weatherService, CityService cityService) {
        this.weatherService = weatherService;
        this.cityService = cityService;
    }

    public void receiveMessage(Long id) {
        String name = cityService.getCityNameById(id);
        String message = "New city " + name + " added successfully!";
        System.out.println("Received <" + message + ">");
        try {
            weatherService.addCityWeather(id);
        } catch (Exception e) {
        }
    }


}