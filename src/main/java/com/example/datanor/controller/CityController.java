package com.example.datanor.controller;

import com.example.datanor.service.CityService;
import com.example.datanor.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
public class CityController {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private CityService cityService;

    @GetMapping("/cities/my")
    public List getMyCities(){
        return cityService.getMyCities();
    }

    @GetMapping("/cities")
    public City getCityByName(@RequestParam("n") String name){
        return cityService.getCityByName(name);
    }


    @GetMapping("/cities/view/{id}")
    public List getCityInfoById(@PathVariable("id") long id){
        return cityService.getCityInfoById(id);
    }

    @PostMapping("cities/add2")
    public void addTrackedCity(@RequestParam("id") long id){
        cityService.addTrackedCity(id);
    }

    @DeleteMapping("cities/{id}")
    public String deleteCity(@PathVariable("id") long id) throws Exception {
        return cityService.deleteCity(id);
    }

}
