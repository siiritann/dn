package com.example.datanor.service;

import com.example.datanor.exception.ApplicationException;
import com.example.datanor.model.City;
import com.example.datanor.repository.CityRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileReader;
import java.util.List;

@Service
public class CityService {

    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }


    // TODO refacto classloaderiga

    @PostConstruct
    public void init() {
        if (countBaseCities() != 0) {
            System.out.println(countBaseCities());
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
                    addCitiesToBase(id, name, country, state);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void addCitiesToBase(long id, String name, String country, String state) {
        cityRepository.addCitiesToBase(id, name, country, state);
    }

    public int countBaseCities() {
        return cityRepository.countBaseCities();
    }

    public List<City> getCityByName(String name) {
        return cityRepository.getCityByName(name);
    }

    public void addTrackedCity(long id) {
        if (cityRepository.getMyCitiesIds().contains(id)) {
            throw new ApplicationException("This city already is in your watchlist");
        } else {
            cityRepository.addTrackedCity(id);
        }
    }

    public List<City> getMyCities() {
        return cityRepository.getMyCities();
    }

    public List<Long> getMyCitiesIds() {
        return cityRepository.getMyCitiesIds();
    }

    public String deleteCity(long id) {
        if (cityRepository.deleteCity(id) == 1) {
            return "City deleted";
        } else {
            throw new ApplicationException("Something went wrong");
        }
    }

    public String getCityNameById(long id) {
        try {
            return cityRepository.getCityNameById(id);
        } catch (Exception e) {
            throw new ApplicationException("City with this ID was not found");
        }
    }

}
