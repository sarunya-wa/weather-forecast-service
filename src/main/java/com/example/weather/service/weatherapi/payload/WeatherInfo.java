package com.example.weather.service.weatherapi.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherInfo {
    private double temp_c;
    private double temp_f;
    private Condition condition;
}
