package com.example.weather.service.weatherapi;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.example.weather.common.Constant;
import com.example.weather.model.CacheResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.example.weather.service.ExternalService;
import com.example.weather.service.weatherapi.payload.ForecastResponse;

@Service
@CacheConfig(
    cacheManager = Constant.CACHE_MANAGER_DEFAULT,
    cacheNames = Constant.CACHE_NAME_FORECAST_INFO
)
public class WeatherService extends ExternalService {
    private final String baseUrl;
    private final String apiKey;

    @Autowired
    public WeatherService(
        @Value("${weatherapi.url}") String apiUrl, 
        @Value("${weatherapi.key}") String apiKey) {
        this.baseUrl = apiUrl + "/v1/forecast.json";
        this.apiKey = apiKey;
    }

    private ForecastResponse getMockResponse() {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            String mockFile = WeatherService.class.getClassLoader().getResource("98665_data.json").getFile();
            ForecastResponse mockForecast = mapper.readValue(new File(mockFile), ForecastResponse.class);
            return mockForecast;
        } catch (IOException e) {
            throw new RuntimeException("Fail to build mock response");
        }
    }

    @Cacheable(key = "#a0")
    public CacheResult<ForecastResponse> getForecast(String zipCode) {
        if (ObjectUtils.isEmpty(this.apiKey)) {
            ForecastResponse mockForecast = getMockResponse();
            return new CacheResult<>(mockForecast, false);
        }

        Map<String, String> params = getForecastParams(zipCode);

        ForecastResponse forecast = executeCommand(baseUrl, HttpMethod.GET, new HashMap<>(), params, null, ForecastResponse.class);
        return new CacheResult<>(forecast, false);
    }

    private Map<String, String> getForecastParams(String zipCode) {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("q", zipCode);
        headers.put("days", "1");
        headers.put("aqi", "no");
        headers.put("alerts", "no");
        headers.put("key", apiKey);
        return headers;
    }
}