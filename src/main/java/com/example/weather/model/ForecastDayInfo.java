package com.example.weather.model;

import lombok.Data;

@Data
public class ForecastDayInfo {
    private double maxTemperatureInCelsius;
    private double minTemperatureInCelsius;
    private double maxTemperatureInFahrenheit;
    private double minTemperatureInFahrenheit;
    private String temperatureDescription;
}
