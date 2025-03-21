package com.example.weather.service.weatherapi.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Day {
    private double maxtemp_c;
    private double mintemp_c;
    private double maxtemp_f;
    private double mintemp_f;
    private Condition condition;
}
