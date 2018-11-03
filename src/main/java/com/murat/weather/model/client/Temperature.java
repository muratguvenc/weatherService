package com.murat.weather.model.client;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Temperature {
    private BigDecimal temp;
    private BigDecimal temp_min;
    private BigDecimal temp_max;
    private BigDecimal pressure;
    private BigDecimal sea_level;
    private BigDecimal grnd_level;
    private BigDecimal humidity;
    private BigDecimal temp_kf;
}
