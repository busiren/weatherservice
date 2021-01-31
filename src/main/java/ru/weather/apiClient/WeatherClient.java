package ru.weather.apiClient;

public interface WeatherClient<T> {

  T  requestWeather(String cityName);

}
