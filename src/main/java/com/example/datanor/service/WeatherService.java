package com.example.datanor.service;

import com.example.datanor.exception.ApplicationException;
import com.example.datanor.model.CityWeather;
import com.example.datanor.repository.WeatherRepository;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
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
            JSONObject myObject = new JSONObject(response.body().string());
            Long cityId = myObject.getLong("id");
            BigDecimal temp = new BigDecimal(myObject.getJSONObject("main").getString("temp"));
            BigDecimal windSpeed = new BigDecimal(myObject.getJSONObject("wind").getString("speed"));
            int humidity = myObject.getJSONObject("main").getInt("humidity");
            CityWeather cityWeather = new CityWeather(cityId, temp, windSpeed, humidity);
            return cityWeather;

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
