package com.murat.weather.service;

import com.murat.weather.model.ForecastResponseDTO;

public interface ForecastService {

    ForecastResponseDTO getForecastByCity(String city);

    void deleteCache();
}
