package com.hytejasvi.journalApp.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class WeatherResponse {

    private Current current;

    @Getter
    @Setter
    public static class Current {
        private Integer temperature;
        @JsonProperty("weather_descriptions")
        private List<String> weatherDescriptions = new ArrayList<String>();
        @JsonProperty("wind_speed")
        private Integer windSpeed;
        @JsonProperty("feelslike")
        private Integer feelsLike;
    }
}


