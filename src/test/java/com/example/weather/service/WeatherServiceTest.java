package com.example.weather.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.example.weather.model.CacheResult;
import com.example.weather.service.weatherapi.payload.ForecastResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.weather.service.weatherapi.WeatherService;

@SpringBootTest
class WeatherServiceTest {
    @Autowired
    private WeatherService weatherService;

    @Test
    void testGetWeather() {
        String address = "98665";
        CacheResult<ForecastResponse> forecast = weatherService.getForecast(address);
        assertNotNull(forecast);
        assertNotNull(forecast.getData());
    }
}