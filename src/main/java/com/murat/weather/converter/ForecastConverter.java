package com.murat.weather.converter;

import com.murat.weather.model.CityDTO;
import com.murat.weather.model.ForecastResponseDTO;
import com.murat.weather.model.PressureDTO;
import com.murat.weather.model.TemperatureDTO;
import com.murat.weather.model.client.City;
import com.murat.weather.model.client.Forecast;
import com.murat.weather.model.client.ForecastListResponse;
import com.murat.weather.util.DateUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ForecastConverter {

    private static int BLOCK_COUNT = 4;
    private static int BLOCK_COUNT_INCLUSIVE = 5;
    private static int DAY_BLOCK_COUNT = 8;
    private static int DAY_BLOCK_COUNT_INCLUSIVE = 9;
    private static int FORECAST_DAY_COUNT = 3;
    private static int INITIAL_INDEX = 0;

    public ForecastResponseDTO convert(@NotNull final ForecastListResponse serviceResponse) {

        final ForecastResponseDTO response = new ForecastResponseDTO();

        if (serviceResponse.getList() == null) {
            return response;
        }

        final List<Forecast> forecastList = serviceResponse.getList();
        response.setTemperatures(getAverageTemperature(forecastList));
        response.setPressures(getAveragePressure(forecastList));

        final City serviceCity = serviceResponse.getCity();
        final CityDTO city = new CityDTO();
        city.setId(String.valueOf(serviceCity.getId()));
        city.setCountry(serviceCity.getCountry());
        city.setName(serviceCity.getName());
        response.setCity(city);

        return response;
    }

    //Returns average temperature list for next 3 days starting from
    //next 06:00:00 with each temperature block contains day and night
    //parts.
    private List<TemperatureDTO> getAverageTemperature(final List<Forecast> forecastList) {

        final List<Forecast> rangedForecastList = CollectionUtils.emptyIfNull(forecastList)
                .stream()
                .filter(Objects::nonNull)
                .filter(this::insideTheThreeDayRange)
                .collect(Collectors.toCollection(ArrayList::new));

        final List<TemperatureDTO> temperatureList = new ArrayList<>();

        if (CollectionUtils.isEmpty(rangedForecastList)) {
            return temperatureList;
        }

        for(int i=0; i<FORECAST_DAY_COUNT; i++) {
            final TemperatureDTO temperature = new TemperatureDTO();
            final BigDecimal dayAverage = getAverageTemperatureForList(rangedForecastList
                    .subList(i* DAY_BLOCK_COUNT, ((i* DAY_BLOCK_COUNT)+ BLOCK_COUNT_INCLUSIVE)));
            final BigDecimal dayMaxDegree = getMaxDegree(rangedForecastList
                    .subList(i* DAY_BLOCK_COUNT, ((i* DAY_BLOCK_COUNT)+ BLOCK_COUNT_INCLUSIVE)));
            final BigDecimal dayMinDegree = getMinDegree(rangedForecastList
                    .subList(i* DAY_BLOCK_COUNT, ((i* DAY_BLOCK_COUNT)+ BLOCK_COUNT_INCLUSIVE)));
            temperature.setDay(dayAverage);
            temperature.setDayMax(dayMaxDegree);
            temperature.setDayMin(dayMinDegree);

            final BigDecimal nightAverage = getAverageTemperatureForList(rangedForecastList
                    .subList(((i+1)* DAY_BLOCK_COUNT - BLOCK_COUNT), ((i+1)* DAY_BLOCK_COUNT+1)));
            final BigDecimal nightMaxDegree = getMaxDegree(rangedForecastList
                    .subList(((i+1)* DAY_BLOCK_COUNT - BLOCK_COUNT), ((i+1)* DAY_BLOCK_COUNT+1)));
            final BigDecimal nightMinDegree = getMinDegree(rangedForecastList
                    .subList(((i+1)* DAY_BLOCK_COUNT - BLOCK_COUNT), ((i+1)* DAY_BLOCK_COUNT+1)));
            temperature.setNight(nightAverage);
            temperature.setNightMax(nightMaxDegree);
            temperature.setNightMin(nightMinDegree);
            temperatureList.add(temperature);
        }

        return temperatureList;
    }

    private BigDecimal getAverageTemperatureForList(final List<Forecast> forecasts) {
        BigDecimal average = BigDecimal.ZERO;
        for (Forecast forecast : forecasts) {
            average = average.add(forecast.getMain().getTemp());
        }
        return average.divide(new BigDecimal(BLOCK_COUNT_INCLUSIVE), 2, BigDecimal.ROUND_HALF_DOWN);
    }

    private BigDecimal getMaxDegree(final List<Forecast> forecasts) {
        BigDecimal maxDegree = forecasts.get(INITIAL_INDEX).getMain().getTemp_max();
        for (Forecast forecast : forecasts) {
            final BigDecimal currentTemperature = forecast.getMain().getTemp_max();
            if (maxDegree.compareTo(currentTemperature) < 0) {
                maxDegree = currentTemperature;
            }
        }
        return maxDegree;
    }

    private BigDecimal getMinDegree(final List<Forecast> forecasts) {
        BigDecimal minDegree = forecasts.get(INITIAL_INDEX).getMain().getTemp_min();
        for (Forecast forecast : forecasts) {
            final BigDecimal currentTemperature = forecast.getMain().getTemp_min();
            if (minDegree.compareTo(currentTemperature) > 0) {
                minDegree = currentTemperature;
            }
        }
        return minDegree;
    }

    //Returns average pressure list for next 3 days starting from
    //next 00:00:00 with each pressure block contains sea level and
    //ground level parts.
    private List<PressureDTO> getAveragePressure(final List<Forecast> forecastList) {
        final List<Forecast> rangedForecastList = CollectionUtils.emptyIfNull(forecastList)
                .stream()
                .filter(Objects::nonNull)
                .filter(this::insideNextThreeDays)
                .collect(Collectors.toCollection(ArrayList::new));

        final List<PressureDTO> pressureList = new ArrayList<>();

        if (CollectionUtils.isEmpty(rangedForecastList)) {
            return pressureList;
        }

        for (int i=0; i<FORECAST_DAY_COUNT; i++) {
            final PressureDTO pressure = getAveragePressureForList(rangedForecastList
                    .subList(i* DAY_BLOCK_COUNT, (i+1) * DAY_BLOCK_COUNT +1));
            pressureList.add(pressure);
        }
        return pressureList;
    }

    private PressureDTO getAveragePressureForList(final List<Forecast> forecasts) {
        BigDecimal seaLevelAverage = BigDecimal.ZERO;
        BigDecimal groundLevelAverage = BigDecimal.ZERO;
        for (Forecast forecast : forecasts) {
            seaLevelAverage = seaLevelAverage.add(forecast.getMain().getSea_level());
            groundLevelAverage = groundLevelAverage.add(forecast.getMain().getGrnd_level());
        }
        seaLevelAverage = seaLevelAverage
                .divide(new BigDecimal(DAY_BLOCK_COUNT_INCLUSIVE), 2, BigDecimal.ROUND_HALF_DOWN);
        groundLevelAverage = groundLevelAverage
                .divide(new BigDecimal(DAY_BLOCK_COUNT_INCLUSIVE), 2, BigDecimal.ROUND_HALF_DOWN);
        final PressureDTO pressure = new PressureDTO();
        pressure.setSeaLevel(seaLevelAverage);
        pressure.setGroundLevel(groundLevelAverage);
        return pressure;
    }

    private boolean insideTheThreeDayRange(final Forecast forecast) {
        final Date forecastDate = DateUtil.asDate(forecast.getDt_txt());
        final Date nextSixAM = DateUtil.nextSixAM();
        final Date lastSixAM = DateUtil.lastSixAM();
        if (forecastDate == null || nextSixAM == null || lastSixAM == null) {
            return false;
        }
        if (forecastDate.before(nextSixAM)) {
            return false;
        } else if (forecastDate.after(lastSixAM)) {
            return false;
        }
        return true;
    }

    private boolean insideNextThreeDays(final Forecast forecast) {
        final Date forecastDate = DateUtil.asDate(forecast.getDt_txt());
        final Date nextSixAM = DateUtil.nextDayStart();
        final Date lastSixAM = DateUtil.lastDayStart();
        if (forecastDate == null || nextSixAM == null || lastSixAM == null) {
            return false;
        }
        if (forecastDate.before(nextSixAM)) {
            return false;
        } else if (forecastDate.after(lastSixAM)) {
            return false;
        }
        return true;
    }
}
