package com.example.datanor.controller;

import com.example.datanor.exception.ApplicationException;
import com.example.datanor.model.City;
import com.example.datanor.service.CityService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.io.FileReader;
import java.util.List;

@CrossOrigin
@RestController
public class CityController {

    @Autowired
    private CityService cityService;


    @PostConstruct
    public void init() {

        if (cityService.countBaseCities() != 0) {
            System.out.println(cityService.countBaseCities());
        } else {
            JSONParser parser = new JSONParser();
            try {
                Object objectArray = parser
                        .parse(new FileReader("src/main/resources/static/city.list.json"));
                JSONArray jsonArray = (JSONArray) objectArray;
                for (Object object : jsonArray) {
                    JSONObject jsonObject = (JSONObject) object;
                    Long id = (Long) jsonObject.get("id");
                    String name = (String) jsonObject.get("name");
                    String country = (String) jsonObject.get("country");
                    String state = (String) jsonObject.get("state");
                    cityService.addCitiesToBase(id, name, country, state);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
