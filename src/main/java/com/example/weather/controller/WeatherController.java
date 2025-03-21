package com.example.weather.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.example.weather.model.Forecast;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.weather.provider.ForecastProvider;

@RestController
public class WeatherController {
    @Autowired
    private ForecastProvider forecastProvider;

    @GetMapping("/forecast")
    @ResponseStatus(HttpStatus.OK)
    public Forecast getForecast(@RequestParam("q") String address) {
        Forecast forecast = forecastProvider.getForecast(address);
        return forecast;
    }
}

