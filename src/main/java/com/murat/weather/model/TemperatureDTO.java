package com.murat.weather.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TemperatureDTO {
    private BigDecimal day;
    private BigDecimal night;
    private BigDecimal min;
    private BigDecimal max;
}
