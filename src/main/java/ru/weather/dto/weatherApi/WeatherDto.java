package ru.weather.dto.weatherApi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WeatherDto {

    private LocationDto location;

    private CurrentDto current;

}
