package com.example.weather.service;

import static org.junit.jupiter.api.Assertions.*;

import com.example.weather.service.weatherapi.ForecastResponseTransformer;
import org.junit.jupiter.api.Test;

import com.example.weather.model.Forecast;
import com.example.weather.model.ForecastDayInfo;
import com.example.weather.service.weatherapi.payload.*;

public class ForecastResponseTransformerTest {

    private final ForecastResponseTransformer transformer = new ForecastResponseTransformer();

    @Test
    void testTransform_withValidForecastResponse() {
        // Arrange
        Location location = new Location();
        location.setName("New York");
        location.setRegion("NY");
        location.setCountry("USA");

        WeatherInfo currentWeather = new WeatherInfo();
        currentWeather.setTemp_c(20.0);
        currentWeather.setTemp_f(68.0);
        Condition condition = new Condition();
        condition.setText("Sunny");
        currentWeather.setCondition(condition);

        Day day = new Day();
        day.setMaxtemp_c(25.0);
        day.setMintemp_c(15.0);
        day.setMaxtemp_f(77.0);
        day.setMintemp_f(59.0);
        Condition dayCondition = new Condition();
        dayCondition.setText("Partly Cloudy");
        day.setCondition(dayCondition);

        ForecastDay forecastDay = new ForecastDay();
        forecastDay.setDay(day);

        ForecastInfo forecastInfo = new ForecastInfo();
        forecastInfo.setForecastday(new ForecastDay[] { forecastDay });

        ForecastResponse forecastResponse = new ForecastResponse();
        forecastResponse.setLocation(location);
        forecastResponse.setCurrent(currentWeather);
        forecastResponse.setForecast(forecastInfo);

        // Act
        Forecast result = transformer.transform(forecastResponse);

        // Assert
        assertNotNull(result);
        assertEquals("Name: New York Region: NY Country: USA", result.getLocationDesc());
        assertEquals("Sunny", result.getCurrentTemperatureDescription());
        assertEquals(20.0, result.getCurrentTemperatureInCelsius());
        assertEquals(68.0, result.getCurrentTemperatureInFahrenheit());

        ForecastDayInfo[] dayInfo = result.getDayInfo();
        assertNotNull(dayInfo);
        assertEquals(1, dayInfo.length);
        assertEquals(25.0, dayInfo[0].getMaxTemperatureInCelsius());
        assertEquals(15.0, dayInfo[0].getMinTemperatureInCelsius());
        assertEquals(77.0, dayInfo[0].getMaxTemperatureInFahrenheit());
        assertEquals(59.0, dayInfo[0].getMinTemperatureInFahrenheit());
        assertEquals("Partly Cloudy", dayInfo[0].getTemperatureDescription());
    }

    @Test
    void testTransform_withNullForecastResponse() {
        // Act
        Forecast result = transformer.transform(null);

        // Assert
        assertNull(result);
    }

    @Test
    void testTransform_withEmptyForecastResponse() {
        // Arrange
        ForecastResponse forecastResponse = new ForecastResponse();

        // Act
        Forecast result = transformer.transform(forecastResponse);

        // Assert
        assertNotNull(result);
        assertNull(result.getLocationDesc());
        assertNull(result.getCurrentTemperatureDescription());
        assertNull(result.getDayInfo());
    }
}