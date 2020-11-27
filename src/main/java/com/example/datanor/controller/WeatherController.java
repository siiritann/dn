package com.example.datanor.controller;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class WeatherController {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @GetMapping("/city")
    private String getWeatherByCityID() throws Exception{
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("http://api.openweathermap.org/data/2.5/weather?q=Narva&appid=0c78a76af15cc5843caa7b5f44e1623e&units=metric")
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();
        String s = response.body().string();
        JSONObject myObject = new JSONObject(s);
        System.out.println(s);
        System.out.println(myObject.get("base"));
        return "TUBLI";
    }
}
