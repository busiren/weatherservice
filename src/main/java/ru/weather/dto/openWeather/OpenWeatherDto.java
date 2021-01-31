package ru.weather.dto.openWeather;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OpenWeatherDto {

    private String name;

    @JsonProperty("main")
    private OpenMetricsDto metrics;

}
