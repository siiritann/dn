package com.example.datanor.service;

import com.example.datanor.DatanorApplication;
import com.example.datanor.exception.ApplicationException;
import com.example.datanor.exception.ObjectNotFoundException;
import com.example.datanor.model.City;
import com.example.datanor.model.UserCity;
import com.example.datanor.repository.CityRepository;
import com.example.datanor.repository.UserCityRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class CityService {

    private final CityRepository cityRepository;
    private final RabbitTemplate rabbitTemplate;
    private final UserCityRepository userCityRepository;

    public CityService(CityRepository cityRepository, RabbitTemplate rabbitTemplate, UserCityRepository userCityRepository) {
        this.cityRepository = cityRepository;
        this.rabbitTemplate = rabbitTemplate;
        this.userCityRepository = userCityRepository;
    }

    Logger logger = LoggerFactory.getLogger(CityService.class);

    @PostConstruct
    public void initRefactored() {
        if (countBaseCities() != 0) {
            logger.info("Number of cities imported: " + countBaseCities());
        } else {
            logger.info("Start city import.");
            StopWatch watch = new StopWatch();
            JSONParser parser = new JSONParser();
            ClassLoader classLoader = getClass().getClassLoader();
            watch.start();
            try (InputStream inputStream = classLoader.getResourceAsStream("static/city.list.json")) {
                Object objectArray = parser.parse(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                JSONArray jsonArray = (JSONArray) objectArray;
                watch.stop();
                logger.info("City JSON parsing finished. Execution time: {} ms", watch.getLastTaskTimeMillis());
                watch.start();
                for (Object object : jsonArray) {
                    JSONObject jsonObject = (JSONObject) object;
                    Long id = (Long) jsonObject.get("id");
                    String name = (String) jsonObject.get("name");
                    String country = (String) jsonObject.get("country");
                    String state = (String) jsonObject.get("state");
                    addCitiesToBase(id, name, country, state);
                }
                watch.stop();
                logger.info("City import finished. Execution time: {} ms", watch.getLastTaskTimeMillis());
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

    @Deprecated
    public void addTrackedCity(long id) {
        if (cityRepository.getMyCitiesIds().contains(id)) {
            throw new ApplicationException("This city already is in your watchlist");
        } else {
            cityRepository.addTrackedCity(id);
            sendMessage(id);
        }
    }

    public void addUserCity(Long cityId) {
        if (userCityRepository.findCityIdsByUserId(1L).contains(cityId)) {
            throw new ApplicationException("This city already is in your watchlist");
        } else {
            userCityRepository.save(new UserCity(cityId, 1L));
        }
    }

    public void sendMessage(Long id) {
        rabbitTemplate.convertAndSend(DatanorApplication.topicExchangeName, "foo.bar.baz",
                id);
    }

    public List<City> getMyCities() {
        return cityRepository.getMyCitiesRefactored(1L);
    }

    public List<Long> getMyCitiesIds() {
        return cityRepository.getMyCitiesIds();
    }

    public String deleteCity(long cityId) {
        try {
            userCityRepository.delete(new UserCity(cityId, 1L));
            return "City deleted";
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Something went wrong");
        }
    }

    public String getCityNameById(long id) {
        try {
            return cityRepository.getCityNameById(id);
        } catch (Exception e) {
            throw new ObjectNotFoundException("City with this ID was not found");
        }
    }

}
