package com.example.weather.model;

import lombok.Data;

@Data
public class CacheResult<T> {
    private final T data;
    private final boolean cached;
}
