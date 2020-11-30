package com.example.datanor.controller;

import com.example.datanor.service.CityService;
import com.example.datanor.service.WeatherService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

@CrossOrigin
@RestController
public class CityController {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private WeatherService weatherService;

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
                        .parse(new FileReader("C:\\Users\\Siiri\\Desktop\\datanor\\src\\main\\resources\\static\\city.list.json"));
                JSONArray jsonArray = (JSONArray) objectArray;
                for (Object object : jsonArray) {
                    JSONObject jsonObject = (JSONObject) object;
                    if (jsonObject.get("id").getClass() == Long.class) {
                        Long id = (Long) jsonObject.get("id");
                        String name = (String) jsonObject.get("name");
                        String country = (String) jsonObject.get("country");
                        String state = (String) jsonObject.get("state");
                        cityService.addCitiesToBase(id, name, country, state);
                    } else {
                        System.out.println(jsonObject.get("id"));
                        System.out.println(jsonObject.get("name"));
                    }
                }
            } catch (FileNotFoundException fe) {
                fe.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

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
