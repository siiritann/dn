package com.example.datanor.service;

import com.example.datanor.exception.ApplicationException;
import com.example.datanor.model.CityWeather;
import com.example.datanor.repository.WeatherRepository;
import okhttp3.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Service
public class WeatherService {

    @Autowired
    private WeatherRepository weatherRepository;


    public URI getUriForCity(long id) throws URISyntaxException {
        String url = "http://api.openweathermap.org/data/2.5/weather?units=metric";
        url += "&appid=0c78a76af15cc5843caa7b5f44e1623e";
        url += "&id=" + id;
        return new URI(url);
    }


    public CityWeather getWeatherByCityId(long id) {

        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            URI url = getUriForCity(id);
            Request request = new Request.Builder()
                    .url(HttpUrl.get(url))
                    .method("GET", null)
                    .build();
            Response response = client.newCall(request).execute();
            JSONParser parser = new JSONParser();
            Object object = parser.parse(response.body().string());
            JSONObject jsonObject = (JSONObject) object;
            Long cityId = (Long) jsonObject.get("id");
            JSONObject main = (JSONObject) jsonObject.get("main");
            BigDecimal temp = new BigDecimal((Double) main.get("temp"));
            Integer humidity = (int) (long) main.get("humidity");
            JSONObject wind = (JSONObject) jsonObject.get("wind");
            BigDecimal windSpeed = new BigDecimal((Double) wind.get("speed"));
            return new CityWeather(cityId, temp, windSpeed, humidity);

        } catch (Exception e) {
            throw new ApplicationException("Couldn't get weather for this city");
        }
    }

    public void addCityWeather(long id) {
        CityWeather cityWeather = getWeatherByCityId(id);
        weatherRepository.addCityWeather(cityWeather);

    }


    public List<CityWeather> getWeatherForOneCity(long id) {
        return weatherRepository.getWeatherForOneCity(id);

    }

}
