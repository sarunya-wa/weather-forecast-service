package com.example.weather.model;

import lombok.Data;

@Data
public class Forecast {
    private double currentTemperatureInFahrenheit;
    private double currentTemperatureInCelsius;
    private String currentTemperatureDescription;

    private String locationDesc;

    private ForecastDayInfo [] dayInfo;

    private boolean cached;
}