package com.murat.weather.config;

import feign.Logger;
import feign.Request;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

@Slf4j
public class FeignClientConfiguration {

    @Value("${openweather.http.connectTimeout:10000}")
    private int connectTimeout;
    @Value("${openweather.http.readTimeout:10000}")
    private int readTimeout;

    @Bean
    public Request.Options options() {
        return new Request.Options(connectTimeout, readTimeout);
    }

    @Bean
    public Logger.Level level() {
        return Logger.Level.FULL;
    }
}
