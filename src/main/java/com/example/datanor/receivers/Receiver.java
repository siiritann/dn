package com.example.datanor.receivers;

import com.example.datanor.service.WeatherService;
import org.springframework.stereotype.Component;

@Component
public class Receiver {


    WeatherService weatherService;

    public Receiver(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    public void receiveMessage(Long id) {
        String message = "New city " + id + " added successfully!";
        weatherService.addCityWeather(id);
        System.out.println("Received <" + message + ">");
    }


}