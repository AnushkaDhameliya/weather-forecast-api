package com.weatherforecast.exception;

public class WebServiceDataNotValidException extends RuntimeException{
    public WebServiceDataNotValidException(String message) {
        super(message);
    }

    public WebServiceDataNotValidException(String message, Throwable cause) {
        super(message, cause);
    }

    public WebServiceDataNotValidException(Throwable cause) {
        super(cause);
    }
}
