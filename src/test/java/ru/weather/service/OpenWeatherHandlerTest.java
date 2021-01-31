package ru.weather.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import ru.weather.apiClient.OpenWeatherClient;
import ru.weather.dto.openWeather.OpenWeatherDto;
import ru.weather.entity.WeatherMetrics;
import ru.weather.utils.MarshalUtils;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OpenWeatherHandlerTest {

    @Autowired
    private OpenWeatherHandler openWeatherHandler;

    @MockBean
    private OpenWeatherClient openWeatherClient;

    @Autowired
    private MarshalUtils marshalUtils;

    @Value("classpath:openWeatherMap.json")
    private Resource openWeatherMap;

    @Test
    public void testHandleWeather() throws IOException {
        OpenWeatherDto openWeatherDto = marshalUtils.fromJson(openWeatherMap.getInputStream(), OpenWeatherDto.class);
        Mockito.when(openWeatherClient.requestWeather(any())).thenReturn(openWeatherDto);
        WeatherMetrics weatherMetrics =  openWeatherHandler.requestWeather("Moscow");
        assertEquals(weatherMetrics.getCityName(), "Moscow");
        assertEquals(weatherMetrics.getHumidity(), Integer.valueOf(80));
        assertEquals(weatherMetrics.getPressure(), Float.valueOf("992.0"));
        assertEquals(weatherMetrics.getTemp(), Float.valueOf("-3.52"));
    }
}
