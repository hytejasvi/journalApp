package com.hytejasvi.journalApp.service;

import com.hytejasvi.journalApp.api.response.WeatherResponse;
import com.hytejasvi.journalApp.cache.AppCache;
import com.hytejasvi.journalApp.constants.Placeholders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class WeatherService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${weather.api.key}")
    private String API_KEY;
    /*we remove the fina as we are not initializing it while declaring
    * we removed the static as during the bean creation the class level variables are not disturbed much and because of which
         the value might not reflect correctly */

    @Autowired
    private AppCache appCache;

    @Autowired
    private RedisService redisService;

    public WeatherResponse getWeather(String city) {
        WeatherResponse weatherResponse = redisService.get("Weather_Of_"+city, WeatherResponse.class);
        if (weatherResponse != null) {
            return weatherResponse;
        } else {
            String final_API = appCache.App_Cache.get(AppCache.keys.WEATHER_API.toString()).replace(Placeholders.CITY, city)
                    .replace(Placeholders.API_KEY, API_KEY);
            log.info("final_API: {}", final_API);
            ResponseEntity<WeatherResponse> response = restTemplate.exchange(final_API, HttpMethod.GET,
                    null, WeatherResponse.class);
            WeatherResponse body =  response.getBody();
            if (body != null) {
                redisService.set("Weather_Of_"+city, body, 300L);
            }
            return body;
        }
    }
}
