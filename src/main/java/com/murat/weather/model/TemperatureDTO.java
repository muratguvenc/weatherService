package com.murat.weather.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TemperatureDTO {
    private BigDecimal day;
    private BigDecimal night;
    private BigDecimal dayMin;
    private BigDecimal dayMax;
    private BigDecimal nightMin;
    private BigDecimal nightMax;
}
