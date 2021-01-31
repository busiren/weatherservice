package ru.weather.dto.openWeather;

import lombok.Data;

@Data
public class OpenMetricsDto {

    private Float temp;

    private Float pressure;

    private Integer humidity;
}
