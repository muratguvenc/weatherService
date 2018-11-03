package com.murat.weather.constant.exception;

import com.murat.weather.constant.ErrorCode;

public class CityNotFoundException extends RuntimeException {

    private final String errorCode;

    public CityNotFoundException() {
        super("City not found");
        this.errorCode = ErrorCode.CITY_NOT_FOUND;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
