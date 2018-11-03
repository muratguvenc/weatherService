package com.murat.weather.service.impl;

import com.murat.weather.client.WeatherClient;
import com.murat.weather.converter.ForecastConverter;
import com.murat.weather.model.ForecastResponseDTO;
import com.murat.weather.model.client.ForecastListResponse;
import com.murat.weather.service.ForecastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ForecastServiceImpl implements ForecastService {

    public static final String FORECAST_CACHE_NAME = "forecast";
    private static final String KEY_TEMPLATE = "#city";

    @Autowired
    private WeatherClient weatherClient;

    @Autowired
    private ForecastConverter converter;

    @Cacheable(value = FORECAST_CACHE_NAME, key = KEY_TEMPLATE, unless = "#result == null")
    public ForecastResponseDTO getForecastByCity(final String city) {
        final ForecastListResponse weatherForecast = weatherClient.getForecastList(city);
        return converter.convert(weatherForecast);
    }

    @CacheEvict(value = FORECAST_CACHE_NAME, allEntries=true)
    public void deleteCache() { }
}
