package com.example.datanor.controller;

import com.example.datanor.exception.ApplicationException;
import com.example.datanor.model.City;
import com.example.datanor.service.CityService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }


    @GetMapping("/cities")
    public List<City> getCityByName(@RequestParam("name") String name) {
        return cityService.getCityByName(name);
    }


    @GetMapping("/cities/my")
    public List<City> getMyCities() {
        return cityService.getMyCities();
    }


    @PostMapping("cities/add")
    public void addTrackedCity(@RequestParam("id") long id) {
        if (id < 1) {
            throw new ApplicationException("Incorrect city name");
        } else {
            cityService.addTrackedCity(id);
        }
    }

    @DeleteMapping("cities/delete")
    public String deleteCity(@RequestParam("id") long id) {
        return cityService.deleteCity(id);
    }

    @GetMapping("cities/view/{id}")
    public String getCityNameById(@PathVariable("id") long id) {
        return cityService.getCityNameById(id);
    }

}
