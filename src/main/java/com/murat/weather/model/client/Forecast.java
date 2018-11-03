package com.murat.weather.model.client;

import lombok.Data;

@Data
public class Forecast {
    private Temperature main;
    private String dt_txt;
}
