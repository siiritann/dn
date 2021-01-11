package com.example.datanor.scheduledtasks;

import com.example.datanor.service.CityService;
import com.example.datanor.service.WeatherService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScheduledTasks {

    private final CityService cityService;
    private final WeatherService weatherService;


    public ScheduledTasks(CityService cityService, WeatherService weatherService) {
        this.cityService = cityService;
        this.weatherService = weatherService;
    }

    @Scheduled(fixedRate = 60000) // milliseconds
    public void test() {
        List<Long> list = cityService.getMyCitiesIds();
        try {
            for (long i : list) {
                weatherService.addCityWeather(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
