package com.hytejasvi.journalApp.service;

import com.hytejasvi.journalApp.api.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {

    @Autowired
    private RestTemplate restTemplate;

    private static final String API_KEY = "0797a6795d6139953589ad16456a2a0d";
    private static final String API = "http://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

    public WeatherResponse getWeather(String city) {
        String final_API = API.replace("CITY", city).replace("API_KEY", API_KEY);
        System.out.println("final_API: "+final_API);
        ResponseEntity<WeatherResponse> response = restTemplate.exchange(final_API, HttpMethod.GET,
                null, WeatherResponse.class);
        return response.getBody();
    }
}
