package com.hytejasvi.journalApp.service;

import com.hytejasvi.journalApp.api.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${weather.api.key}")
    private String API_KEY;
    /*we remove the fina as we are not initializing it while declaring
    * we removed the static as during the bean creation the class level variables are not disturbed much and because of which
         the value might not reflect correctly */
    
    private static final String API = "http://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

    public WeatherResponse getWeather(String city) {
        String final_API = API.replace("CITY", city).replace("API_KEY", API_KEY);
        System.out.println("final_API: "+final_API);
        ResponseEntity<WeatherResponse> response = restTemplate.exchange(final_API, HttpMethod.GET,
                null, WeatherResponse.class);
        return response.getBody();
    }
}
