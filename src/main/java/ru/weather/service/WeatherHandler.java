package ru.weather.service;

import org.springframework.http.ResponseEntity;
import ru.weather.entity.WeatherMetrics;

import java.util.Optional;

public interface WeatherHandler<T> {

   WeatherMetrics requestWeather(String cityName);

   WeatherMetrics mapWeather(T weatherDto);

}
