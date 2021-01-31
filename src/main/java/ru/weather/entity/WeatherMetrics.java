package ru.weather.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
public class WeatherMetrics implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime date;

    private String cityName;

    private String source;

    private Float temp;

    private Float pressure;

    private Integer humidity;


}
