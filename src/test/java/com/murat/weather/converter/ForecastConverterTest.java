package com.murat.weather.converter;

import com.murat.weather.model.ForecastResponseDTO;
import com.murat.weather.model.client.City;
import com.murat.weather.model.client.Forecast;
import com.murat.weather.model.client.ForecastListResponse;
import com.murat.weather.model.client.Temperature;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class ForecastConverterTest {

    private final int ZERO = 0;
    private ForecastConverter converter = new ForecastConverter();
    private ForecastListResponse serviceResponse = new ForecastListResponse();

    @Before
    public void createServiceResponse() {
        final List<Forecast> list = new ArrayList<>();

        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (int i=0; i<50; i++) {
            final Forecast forecast = new Forecast();
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            cal.set(Calendar.HOUR_OF_DAY, ZERO);
            cal.set(Calendar.MINUTE, ZERO);
            cal.set(Calendar.SECOND, ZERO);
            cal.set(Calendar.MILLISECOND, ZERO);
            cal.add(Calendar.HOUR, i*3);
            forecast.setDt_txt(dateFormat.format(cal.getTime()));

            final Temperature temperature = new Temperature();
            temperature.setTemp(new BigDecimal(10));
            temperature.setTemp_max(new BigDecimal(11));
            temperature.setTemp_min(new BigDecimal(9));
            temperature.setSea_level(new BigDecimal(1000));
            temperature.setGrnd_level(new BigDecimal(980));
            forecast.setMain(temperature);
            list.add(forecast);
        }

        serviceResponse.setList(list);
        final City city = new City();
        city.setCountry("TR");
        city.setId(123l);
        city.setName("Istanbul");
        serviceResponse.setCity(city);
    }

    @Test
    public void forecastTemperatureConverterTest() {

        final ForecastResponseDTO response = converter.convert(serviceResponse);
        assertTrue(new BigDecimal("10").compareTo(response.getTemperatures().get(0).getDay()) == 0);
        assertTrue(new BigDecimal("11").compareTo(response.getTemperatures().get(0).getDayMax()) == 0);
        assertTrue(new BigDecimal("9").compareTo(response.getTemperatures().get(0).getDayMin()) == 0);
        assertTrue(new BigDecimal("10").compareTo(response.getTemperatures().get(0).getNight()) == 0);
        assertTrue(new BigDecimal("11").compareTo(response.getTemperatures().get(0).getNightMax()) == 0);
        assertTrue(new BigDecimal("9").compareTo(response.getTemperatures().get(0).getNightMin()) == 0);

    }

    @Test
    public void forecastPressureConverterTest() {

        final ForecastResponseDTO response = converter.convert(serviceResponse);
        assertTrue(new BigDecimal("1000").compareTo(response.getPressures().get(0).getSeaLevel()) == 0);
        assertTrue(new BigDecimal("980").compareTo(response.getPressures().get(0).getGroundLevel()) == 0);
    }

    @Test
    public void cityTest() {

        final ForecastResponseDTO response = converter.convert(serviceResponse);
        assertEquals("123",response.getCity().getId());
        assertEquals("TR",response.getCity().getCountry());
    }
}
