package ru.weather.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.weather.apiClient.OpenWeatherClient;
import ru.weather.dto.openWeather.OpenWeatherDto;
import ru.weather.entity.WeatherMetrics;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.DoubleUnaryOperator;

@Slf4j
@Service
@RequiredArgsConstructor
public class OpenWeatherHandler implements WeatherHandler<OpenWeatherDto> {

    private static final DoubleUnaryOperator K_TO_C = d -> Math.round((d - 273) * 100.0) / 100.0;
    public static final String OPENWEATHERMAP = "openweathermap";

    private final OpenWeatherClient openWeatherApiClient;


    @Override
    public WeatherMetrics requestWeather(String cityName) {
       return Optional.ofNullable(openWeatherApiClient.requestWeather(cityName))
                .map(this::mapWeather)
                .orElse(null);
    }


    @Override
    public WeatherMetrics mapWeather(OpenWeatherDto weatherDto) {
        if (weatherDto == null || weatherDto.getMetrics() == null) {
            log.info("Cannot map response from http://api.openweathermap.org/data/2.5/weather");
            return null;
        }
        try {
            WeatherMetrics metrics = new WeatherMetrics();
            metrics.setCityName(weatherDto.getName());
            metrics.setSource(OPENWEATHERMAP);
            metrics.setTemp((float) K_TO_C.applyAsDouble(weatherDto.getMetrics().getTemp()));
            metrics.setPressure(weatherDto.getMetrics().getPressure());
            metrics.setHumidity(weatherDto.getMetrics().getHumidity());
            metrics.setDate(LocalDateTime.now());
            return metrics;
        } catch (Exception ex) {
            log.error("Error on mapping weather response ", ex);
            return null;
        }
    }
}
