package com.murat.weather.client;

import com.murat.weather.config.FeignClientConfiguration;
import com.murat.weather.constant.exception.CityNotFoundException;
import com.murat.weather.model.client.ForecastListResponse;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Component
public class WeatherClient {

    public static final String GET_WEATHER_FORECAST = "/forecast?q={city}&units={unit}&APPID={apikey}";

    @Autowired
    private WeatherClientFeign client;

    @Value("${openweather.api.key}")
    private String apikey;
    @Value("${openweather.api.unit}")
    private String unit;

    public ForecastListResponse getForecastList(final String city) {
        try {
            return client.getForecastList(city, unit, apikey);
        } catch (FeignException ex) {
            if (HttpStatus.NOT_FOUND.equals(HttpStatus.valueOf(ex.status()))) {
                throw new CityNotFoundException();
            } else {
                throw ex;
            }
        }
    }

    @FeignClient(
            name = "cityClientFeign",
            url = "${openweather.http.endpoint}",
            configuration = FeignClientConfiguration.class)
    interface WeatherClientFeign {
        @RequestMapping(value = GET_WEATHER_FORECAST, method = RequestMethod.GET)
        @ResponseBody
        ForecastListResponse getForecastList(@RequestParam("city") final String city,
                @RequestParam("unit") final String unit, @PathVariable("apikey") final String apikey);
    }
}
