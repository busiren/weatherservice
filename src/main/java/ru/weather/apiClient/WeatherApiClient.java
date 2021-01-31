package ru.weather.apiClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.weather.dto.openWeather.OpenWeatherDto;
import ru.weather.dto.weatherApi.WeatherDto;

import java.util.Optional;
import java.util.function.DoubleUnaryOperator;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherApiClient implements WeatherClient<WeatherDto> {

    private static final String URL = "http://api.weatherapi.com/v1/current.json?key=";
    private final RestTemplate restTemplate;
    @Value("${weatherApi.key}")
    private String key;


    @Override
    public WeatherDto requestWeather(String cityName) {
        String url = URL + key + "&q=" + cityName;
        try {
            return Optional.ofNullable(restTemplate.getForEntity(url, WeatherDto.class))
                    .map(ResponseEntity::getBody)
                    .orElse(null);
        } catch (Exception ex) {
            log.error("Error for request: {}", url, ex);
            return null;
        }
    }
}
