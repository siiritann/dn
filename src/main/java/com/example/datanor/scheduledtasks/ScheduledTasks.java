package com.example.datanor.scheduledtasks;

import com.example.datanor.service.CityService;
import com.example.datanor.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScheduledTasks {

    @Autowired
    private CityService cityService;

    @Autowired
    private WeatherService weatherService;

    @Scheduled(fixedRate = 900000)
    public void test(){
        List<Long> list = cityService.getMyCitiesIds();
        try {
            for (long i : list){
                weatherService.addCityWeather(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
