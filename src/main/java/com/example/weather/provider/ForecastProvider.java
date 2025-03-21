package com.example.weather.provider;

import com.example.weather.model.CacheResult;
import com.example.weather.service.weatherapi.ForecastResponseTransformer;
import com.example.weather.service.weatherapi.WeatherServiceWrapper;
import org.springframework.stereotype.Service;

import com.example.weather.model.Forecast;
import com.example.weather.service.weatherapi.payload.ForecastResponse;
import com.example.weather.util.ForecastInputValidatorUtil;

@Service
public class ForecastProvider {
    private final WeatherServiceWrapper weatherService;

    public ForecastProvider(WeatherServiceWrapper weatherService) {
        this.weatherService = weatherService;
    }

    public Forecast getForecast(String address) {
        String zipCode = ForecastInputValidatorUtil.extractZipCodeFromAddress(address);

        CacheResult<ForecastResponse> weatherForecastInfo = weatherService.getForecast(zipCode);
        ForecastResponse weatherForecastResponse = weatherForecastInfo.getData();
        Forecast forecast = new ForecastResponseTransformer().transform(weatherForecastResponse);
        forecast.setCached(weatherForecastInfo.isCached());
        return forecast;
    }

}