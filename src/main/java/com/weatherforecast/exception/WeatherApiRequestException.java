package com.weatherforecast.exception;

public class WeatherApiRequestException extends RuntimeException{

    public WeatherApiRequestException(String message) {
        super(message);
    }

    public WeatherApiRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public WeatherApiRequestException(Throwable cause) {
        super(cause);
    }
}
