package com.example.datanor.service;

import com.example.datanor.exception.ApplicationException;
import com.example.datanor.model.City;
import com.example.datanor.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

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
        return cityRepository.getCityNameById(id);
    }

}
