package com.murat.weather.model;

import lombok.Data;

import java.util.List;

@Data
public class ForecastResponseDTO {
    private List<TemperatureDTO> temperatures;
    private List<PressureDTO> pressures;
    private CityDTO city;
}
