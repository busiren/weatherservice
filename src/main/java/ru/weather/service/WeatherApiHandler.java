package ru.weather.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.weather.apiClient.WeatherApiClient;
import ru.weather.dto.weatherApi.WeatherDto;
import ru.weather.entity.WeatherMetrics;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.DoubleUnaryOperator;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherApiHandler implements WeatherHandler<WeatherDto> {

    private static final DoubleUnaryOperator F_TO_C = d -> Math.round((5 / 9.0) * (d - 32) * 100.0) / 100.0;
    public static final String WEATHERAPI = "weatherapi";
    private final WeatherApiClient weatherApiClient;

    @Override
    public WeatherMetrics requestWeather(String cityName) {
        return Optional.ofNullable(weatherApiClient.requestWeather(cityName))
                .map(this::mapWeather)
                .orElse(null);
    }

    @Override
    public WeatherMetrics mapWeather(WeatherDto weatherDto) {
        if (weatherDto == null || weatherDto.getCurrent() == null) {
            log.info("Cannot map response from http://api.weatherapi.com");
            return null;
        }
        try {
            WeatherMetrics metrics = new WeatherMetrics();
            metrics.setCityName(weatherDto.getLocation().getName());
            metrics.setSource(WEATHERAPI);
            metrics.setTemp(weatherDto.getCurrent().getTemp());
            metrics.setPressure(weatherDto.getCurrent().getPressure());
            metrics.setHumidity(weatherDto.getCurrent().getHumidity());
            metrics.setDate(LocalDateTime.now());
            return metrics;
        } catch (Exception ex) {
            log.error("Error on mapping weather response ", ex);
            return null;
        }
    }
}
