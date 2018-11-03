package com.murat.weather.model.client;

import lombok.Data;

@Data
public class City {
    private long id;
    private String name;
    private String country;
    private long population;
    private GeoCoordinate coord;
}
