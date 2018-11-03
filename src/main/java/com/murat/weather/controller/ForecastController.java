package com.murat.weather.controller;

import com.murat.weather.model.ForecastResponseDTO;
import com.murat.weather.service.ForecastService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import swaggergen.controller.WeatherApi;
import swaggergen.model.ForecastResponse;

@RestController
public class ForecastController implements WeatherApi {

    @Autowired
    private ForecastService forecastService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseEntity<ForecastResponse> weatherForecast(@PathVariable("city") final String city) {

        final ForecastResponseDTO responseDTO = forecastService.getForecastByCity(city);
        final ForecastResponse response = modelMapper.map(responseDTO, ForecastResponse.class);
        response.setSuccess(true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> clearCache() {

        forecastService.deleteCache();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
