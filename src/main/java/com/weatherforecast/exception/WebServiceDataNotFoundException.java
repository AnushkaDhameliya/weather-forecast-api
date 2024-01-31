package com.weatherforecast.exception;

public class WebServiceDataNotFoundException extends RuntimeException{
    public WebServiceDataNotFoundException(String message) {
        super(message);
    }

    public WebServiceDataNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public WebServiceDataNotFoundException(Throwable cause) {
        super(cause);
    }
}
