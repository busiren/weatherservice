package ru.weather.apiClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.weather.dto.openWeather.OpenWeatherDto;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OpenWeatherClient implements WeatherClient<OpenWeatherDto> {

    private static final String URL = "http://api.openweathermap.org/data/2.5/weather?q=";
    private static final String APPID = "&APPID=";
    private final RestTemplate restTemplate;
    @Value("${openWeatherMap.key}")
    private String key;

    @Override
    public OpenWeatherDto requestWeather(String cityName) {
        String url = URL + cityName + APPID + key;
        try {
            return Optional.ofNullable(restTemplate.getForEntity(url, OpenWeatherDto.class))
                    .map(ResponseEntity::getBody)
                    .orElse(null);
        } catch (Exception ex) {
            log.error("Error for request: {}", url, ex);
            return null;
        }
    }
}
