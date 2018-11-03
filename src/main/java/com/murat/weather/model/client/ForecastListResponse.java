package com.murat.weather.model.client;

import lombok.Data;

import java.util.List;

@Data
public class ForecastListResponse {
    private String cod;
    private String message;
    private int cnt;
    private City city;
    private List<Forecast> list;
}
