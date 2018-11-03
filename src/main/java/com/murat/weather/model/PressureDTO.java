package com.murat.weather.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PressureDTO {
    private BigDecimal seaLevel;
    private BigDecimal groundLevel;
}
