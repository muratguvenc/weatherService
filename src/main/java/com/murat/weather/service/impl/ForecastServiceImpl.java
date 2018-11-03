package com.murat.weather.service.impl;

import com.murat.weather.client.WeatherClient;
import com.murat.weather.model.ForecastResponseDTO;
import com.murat.weather.model.client.ForecastListResponse;
import com.murat.weather.service.ForecastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ForecastServiceImpl implements ForecastService {

    @Autowired
    private WeatherClient weatherClient;

    public ForecastResponseDTO getForecastByCity(final String city) {

        final ForecastListResponse weatherForecast = weatherClient.getForecastList(city);
        //TODO add converter
        return null;
    }
}
