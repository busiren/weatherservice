package ru.weather.dto.weatherApi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CurrentDto {

    @JsonProperty("temp_c")
    private Float temp;

    @JsonProperty("pressure_mb")
    private Float pressure;

    private Integer humidity;

}
