package ru.weather.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;
import ru.weather.apiClient.WeatherApiClient;
import ru.weather.dto.openWeather.OpenWeatherDto;
import ru.weather.dto.weatherApi.WeatherDto;
import ru.weather.entity.WeatherMetrics;
import ru.weather.utils.MarshalUtils;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WeatherApiHandlerTest {

    @Autowired
    private WeatherApiHandler weatherApiHandler;

    @MockBean
    private WeatherApiClient weatherApiClient;

    @Autowired
    private MarshalUtils marshalUtils;

    @Value("classpath:weatherApi.json")
    private Resource weatherApi;

    @Test
    public void testHandleWeather() throws IOException {
        WeatherDto weatherDto = marshalUtils.fromJson(weatherApi.getInputStream(), WeatherDto.class);
        Mockito.when(weatherApiClient.requestWeather(any())).thenReturn(weatherDto);
        WeatherMetrics weatherMetrics =  weatherApiHandler.requestWeather("Moscow");
        assertEquals(weatherMetrics.getCityName(), "Moscow");
        assertEquals(weatherMetrics.getHumidity(), Integer.valueOf(80));
        assertEquals(weatherMetrics.getPressure(), Float.valueOf("995.0"));
        assertEquals(weatherMetrics.getTemp(), Float.valueOf("-4.0"));

    }
}
