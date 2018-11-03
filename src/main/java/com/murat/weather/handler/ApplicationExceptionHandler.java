package com.murat.weather.handler;

import com.murat.weather.constant.ErrorCode;
import com.murat.weather.constant.exception.CityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import swaggergen.model.BaseResponse;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationExceptionHandler.class);

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public BaseResponse internalError(final RuntimeException ex) {

        LOGGER.error("technical error , error code : {} - {} ", ErrorCode.TECHNICAL_ERROR, ex.getMessage());
        return createErrorMessageResponse(ex, ErrorCode.TECHNICAL_ERROR);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CityNotFoundException.class)
    public BaseResponse cityNotFoundException(final CityNotFoundException ex) {

        LOGGER.error("technical error , error code : {} - {} ", ex.getErrorCode(), ex.getMessage());
        return createErrorMessageResponse(ex, ex.getErrorCode());
    }

    private BaseResponse createErrorMessageResponse(final RuntimeException ex, final String errorCode) {
        BaseResponse response = new BaseResponse();
        response.setErrorCode(errorCode);
        response.setErrorMessage(ex.getMessage());
        return response;
    }
}
