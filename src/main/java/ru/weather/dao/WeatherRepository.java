package ru.weather.dao;

import org.springframework.data.repository.CrudRepository;
import ru.weather.entity.WeatherMetrics;

public interface WeatherRepository extends CrudRepository<WeatherMetrics, String> {
}
