package com.murat.weather.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CityDTO {
    private String id;
    private String name;
    private String country;
}
