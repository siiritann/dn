package com.example.datanor.controller;

import com.example.datanor.exception.ApplicationException;
import com.example.datanor.model.City;
import com.example.datanor.service.CityService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@Api(description = "Set of endpoints for searching cities, adding, viewing and deleting my cities.")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }


    @GetMapping("/cities")
    public List<City> getCityByName(@RequestParam("name") String name) {
        return cityService.getCityByName(name);
    }


    @ApiOperation("Returns list of my cities.")
    @GetMapping("/cities/my")
    public List<City> getMyCities() {
        return cityService.getMyCities();
    }


    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Incorrect city name")
    })
    @PostMapping("cities/add")
    public void addTrackedCity(@ApiParam("id of the city you want to add to your list")
                               @RequestParam("id") long id) {
        if (id < 1) {
            throw new ApplicationException("Incorrect city name");
        } else {
            cityService.addTrackedCity(id);
        }
    }

    @ApiOperation(value = "Delete your city")
    @DeleteMapping("cities/delete")
    public String deleteCity(@RequestParam("id") long id) {
        return cityService.deleteCity(id);
    }

    @GetMapping("cities/view/{id}")
    public String getCityNameById(@PathVariable("id") long id) {
        return cityService.getCityNameById(id);
    }

}
