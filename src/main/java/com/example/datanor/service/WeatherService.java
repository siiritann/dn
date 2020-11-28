package com.example.datanor.service;

import com.example.datanor.controller.City;
import com.example.datanor.controller.CityRowMapper;
import com.example.datanor.controller.CityWeather;
import com.example.datanor.repository.WeatherRepository;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WeatherService {

    @Autowired
    private WeatherRepository weatherRepository;


    public URI appendUri(long id) throws URISyntaxException {

        String baseUri = "http://api.openweathermap.org/data/2.5/weather?units=metric";
        String appid = "0c78a76af15cc5843caa7b5f44e1623e";

        URI oldUri = new URI(baseUri);

        String newQuery = oldUri.getQuery();
        newQuery += "&appid=" + appid + "&id=" + String.valueOf(id);

        return new URI(oldUri.getScheme(), oldUri.getAuthority(),
                oldUri.getPath(), newQuery, oldUri.getFragment());

    }

    public void addCity(long id, String name, String country) {
        weatherRepository.addCity(id, name, country);
    }
    // TODO ADD READ FROM JSON HERE
/*    public String addCity(long id) throws Exception{
       CityWeather cityWeather = getWeatherByCityId(id);
       List list = weatherRepository.getCityInfoById(id);
       JSONObject myObject = new JSONObject((String) list.get(0));
       String name = myObject.getString("name");
       String country = myObject.getString("countryCode");
        return weatherRepository.addCity(id, name, country);
    }*/

    public CityWeather getWeatherByCityId(long id) throws Exception{
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        URI url = appendUri(id);
        Request request = new Request.Builder()
                .url(HttpUrl.get(url))
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();
        JSONObject myObject = new JSONObject(response.body().string());
        Long cityId = myObject.getLong("id");
        BigDecimal temp = new BigDecimal(myObject.getJSONObject("main").getString("temp"));
        double windSpeed = myObject.getJSONObject("wind").getDouble("speed");
        int humidity = myObject.getJSONObject("main").getInt("humidity");
        CityWeather cityWeather = new CityWeather(cityId, temp, windSpeed, humidity);
        return cityWeather;
    }

    public void addCityWeather(long id) throws Exception{
        CityWeather cityWeather = getWeatherByCityId(id);
        weatherRepository.addCityWeather(cityWeather);

    }

    public List getAllWeatherData() {
        return weatherRepository.getAllWeatherData();
    }

    public List getMyCities(){
        return weatherRepository.getMyCities();
    }

    public List getWeatherForOneCity(long id){
        return weatherRepository.getWeatherForOneCity(id);

    }

    public String deleteCity(long id){
        return weatherRepository.deleteCity(id);
    }

    public List getCityInfoById(long id){
        return weatherRepository.getCityInfoById(id);
    }
}
