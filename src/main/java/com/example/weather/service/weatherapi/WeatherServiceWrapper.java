package com.example.weather.service.weatherapi;

import com.example.weather.aspect.CacheCounter;
import com.example.weather.model.CacheResult;
import com.example.weather.service.weatherapi.payload.ForecastResponse;
import org.springframework.stereotype.Service;

@Service
public class WeatherServiceWrapper {
    private final WeatherService weatherService;

    public WeatherServiceWrapper(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @CacheCounter
    public CacheResult<ForecastResponse> getForecast(String zipCode) {
        return weatherService.getForecast(zipCode);
    }

}
