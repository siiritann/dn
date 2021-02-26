package com.example.datanor.service;

import com.example.datanor.exception.ApplicationException;
import com.example.datanor.exception.InternalException;
import com.example.datanor.model.Weather;
import com.example.datanor.repository.WeatherRepositoryHibernate;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Service
public class WeatherService {

    private final CityService cityService;

    public WeatherService(CityService cityService) {
        this.cityService = cityService;
    }

    @Autowired
    private WeatherRepositoryHibernate weatherRepositoryHibernate;

    @Value("${weatherMap.url}")
    private String url;

    @Value("${weatherMap.appid}")
    private String appid;

    public URI getUriForCity(long id) throws URISyntaxException {
        String updatedUrl = url;
        updatedUrl += "&appid=" + appid;
        updatedUrl += "&id=" + id;
        return new URI(updatedUrl);
    }

    public Weather getWeatherByCityId(long id) {
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
            BigDecimal temp = BigDecimal.valueOf((Double) main.get("temp")); // TODO fix if raw data is int
            int humidity = (int) (long) main.get("humidity");
            JSONObject wind = (JSONObject) jsonObject.get("wind");
            BigDecimal windSpeed = BigDecimal.valueOf((Double) wind.get("speed"));
            return new Weather(cityId, temp, windSpeed, humidity);

        } catch (Exception e) {
            throw new ApplicationException("Couldn't get weather for this city", e);
        }
    }

    public void addCityWeather(long id) {
        if (cityService.getCityNameById(id).length() > 0) {
            try {
                Weather weather = getWeatherByCityId(id);
                weatherRepositoryHibernate.save(weather);
            } catch (Exception e) {
                throw new InternalException("Something went wrong", e);
            }
        }
    }

    public List<Weather> getWeatherForOneCity(Long id) {
        return weatherRepositoryHibernate.getWeatherByCityId(id);

    }
}
