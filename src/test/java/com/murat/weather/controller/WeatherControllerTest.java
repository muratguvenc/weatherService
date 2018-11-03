package com.murat.weather.controller;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WeatherControllerTest {

    final String FORECAST_URL_FOR_ISTANBUL = "/weather/Istanbul/forecast";

    @Autowired
    private ForecastController forecastController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(forecastController).build();
    }

    @Test
    public void testCityForecast() throws Exception{
        //Mocking Controller
        forecastController = mock(ForecastController.class);

        this.mockMvc.perform(get(FORECAST_URL_FOR_ISTANBUL)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is5xxServerError());
    }

}