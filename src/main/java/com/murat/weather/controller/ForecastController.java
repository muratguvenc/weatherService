package com.murat.weather.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import swaggergen.controller.WeatherApi;
import swaggergen.model.ForecastResponse;

@RestController
public class ForecastController implements WeatherApi {

    @Override
    public ResponseEntity<ForecastResponse> weatherForecast(@PathVariable("city") final String city) {

        //TODO implement backend service call
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}
