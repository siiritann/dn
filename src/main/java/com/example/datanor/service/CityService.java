package com.example.datanor.service;

import com.example.datanor.controller.City;
import com.example.datanor.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    public void addCitiesToBase(long id, String name, String country, String state){
        cityRepository.addCitiesToBase(id, name, country, state);
    }

    public int countBaseCities(){
        return cityRepository.countBaseCities();
    }

    public void addTrackedCity(long id){
       cityRepository.addTrackedCity(id);
    }

    public List getMyCities(){
        return cityRepository.getMyCities();
    }


    // TODO merge with getMyCities
    public List<Long> getMyCitiesIds(){
        return cityRepository.getMyCitiesIds();
    }

    public String deleteCity(long id) throws Exception {
        if(cityRepository.deleteCity(id) == 1){
            return "City deleted";
        } else {
            throw new Exception("Something went wrong");
        }
    }

    public City getCityByName(String name) {
        return cityRepository.getCityByName(name);
    }

    public List getCityInfoById(long id){
        return cityRepository.getCityInfoById(id);
    }

}
