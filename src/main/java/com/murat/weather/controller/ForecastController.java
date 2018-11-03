package com.murat.weather.controller;

import com.murat.weather.service.ForecastService;
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

    @Override
    public ResponseEntity<ForecastResponse> weatherForecast(@PathVariable("city") final String city) {

        forecastService.getForecastByCity(city);
        //TODO convert to response object
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}
