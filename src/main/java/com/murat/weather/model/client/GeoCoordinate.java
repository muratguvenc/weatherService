package com.murat.weather.model.client;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class GeoCoordinate {
    private BigDecimal lon;
    private BigDecimal lat;
}
