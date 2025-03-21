package com.example.weather.service.weatherapi;

import com.example.weather.model.ForecastDayInfo;
import com.example.weather.service.weatherapi.payload.*;

import com.example.weather.model.Forecast;

public class ForecastResponseTransformer {
    public Forecast transform(ForecastResponse from) {
        if (null == from) return null;

        Forecast to = new Forecast();

        to.setLocationDesc(getLocationDescription(from.getLocation()));
        to.setDayInfo(transformForecastInfo(from.getForecast()));
        to = transformWeatherInfo(to, from.getCurrent());
 
        return to;
    }

    private String getLocationDescription(Location from) {
        if (null == from) return null;

        return String.format("Name: %s Region: %s Country: %s", from.getName(), from.getRegion(), from.getCountry());
    }

    private Forecast transformWeatherInfo(Forecast to, WeatherInfo from) {
        if (null == from) return to;

        if (null != from.getCondition()) {
            to.setCurrentTemperatureDescription(from.getCondition().getText());
        }

        to.setCurrentTemperatureInCelsius(from.getTemp_c());
        to.setCurrentTemperatureInFahrenheit(from.getTemp_f());
        return to;
    }
    private ForecastDayInfo[] transformForecastInfo(ForecastInfo from) {
        if (null == from) 
            return null;
        if (null == from.getForecastday() || 0 == from.getForecastday().length)
            return null;

        ForecastDay[] fromDays = from.getForecastday();
        ForecastDayInfo [] days = new ForecastDayInfo[fromDays.length];
        for (int i = 0; i < fromDays.length; ++i) {
            Day fromDay = fromDays[i].getDay();
            if (null == fromDay)
                continue;

            if (null == fromDay)
                continue;

            days[i] = new ForecastDayInfo();
            days[i].setMaxTemperatureInCelsius(fromDay.getMaxtemp_c());
            days[i].setMinTemperatureInCelsius(fromDay.getMintemp_c());
            days[i].setMaxTemperatureInFahrenheit(fromDay.getMaxtemp_f());
            days[i].setMinTemperatureInFahrenheit(fromDay.getMintemp_f());

            if (null == fromDay.getCondition()) 
                continue;

            days[i].setTemperatureDescription(fromDay.getCondition().getText());
        }

        return days;
    }
}