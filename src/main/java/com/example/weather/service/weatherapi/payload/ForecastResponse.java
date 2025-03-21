package com.example.weather.service.weatherapi.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ForecastResponse {
    private Location location;
    private WeatherInfo current;
    private ForecastInfo forecast;
}