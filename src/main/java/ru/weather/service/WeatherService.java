package ru.weather.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.weather.dao.WeatherRepository;
import ru.weather.entity.WeatherMetrics;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
@AllArgsConstructor
public class WeatherService {

    private final List<WeatherHandler> handlers;
    private final WeatherRepository repository;

    public void handleCityWeather(String cityName) {
        handlers.stream()
                .map(handler -> handler.requestWeather(cityName))
                .forEach(this::saveWeather);
    }

    public List<WeatherMetrics> findAllMetrics() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    private void saveWeather(WeatherMetrics weatherMetrics) {
        if (weatherMetrics != null) {
            WeatherMetrics metrics = repository.save(weatherMetrics);
            log.info("Save weather metrics {}", metrics);
        }
    }
}
