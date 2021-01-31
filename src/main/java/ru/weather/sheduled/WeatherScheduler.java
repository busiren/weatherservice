package ru.weather.sheduled;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.weather.service.WeatherService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherScheduler {

    private final WeatherService weatherService;

    @Value("${cities}")
    private final List<String> cities = new ArrayList<>();

    @Scheduled(cron = "${cron}")
    public void saveWeather() {
        cities.forEach(weatherService::handleCityWeather);
    }

}
