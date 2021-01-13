package com.example.datanor.service;

import com.example.datanor.exception.ApplicationException;
import com.example.datanor.exception.InternalException;
import com.example.datanor.model.Weather;
import com.example.datanor.repository.WeatherRepository;
import com.example.datanor.repository.WeatherRepositoryHibernate;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Service
public class WeatherService {

    private final WeatherRepository weatherRepository;

    private final CityService cityService;

    public WeatherService(WeatherRepository weatherRepository, @Lazy CityService cityService) {
        this.weatherRepository = weatherRepository;
        this.cityService = cityService;
    }

    @Autowired
    private WeatherRepositoryHibernate weatherRepositoryHibernate;

    @Value("${datanor.weatherMap.url}")
    private String url;

    @Value("${datanor.weatherMap.appid}")
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
            BigDecimal temp = new BigDecimal((Double) main.get("temp"));
            Integer humidity = (int) (long) main.get("humidity");
            JSONObject wind = (JSONObject) jsonObject.get("wind");
            BigDecimal windSpeed = new BigDecimal((Double) wind.get("speed"));
            return new Weather(cityId, temp, windSpeed, humidity);

        } catch (Exception e) {
            throw new ApplicationException("Couldn't get weather for this city", e);
        }
    }


    public void addCityWeather(long id) {
        if (cityService.getCityNameById(id).length() > 0) {
            try {
//                CityWeather cityWeather = getWeatherByCityId(id);
//                weatherRepository.addCityWeather(cityWeather);
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
