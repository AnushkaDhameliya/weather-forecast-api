package com.weatherforecast.exception;

import com.weatherforecast.logger.LoggerService;
import com.weatherforecast.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@ControllerAdvice
public class WebServiceExceptionHandler {

    @Autowired
    private LoggerService loggerService;

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(WebServiceException webServiceException){
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                webServiceException.getMessage(),
                System.currentTimeMillis()
        );

        loggerService.displayErrorLog(errorResponse.toString());

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(WebServiceDataNotFoundException webServiceDataNotFoundException){
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                webServiceDataNotFoundException.getMessage(),
                System.currentTimeMillis()
        );

        loggerService.displayErrorLog(errorResponse.toString());

        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(WebServiceDataNotValidException exception){
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_ACCEPTABLE.value(),
                exception.getMessage(),
                System.currentTimeMillis()
        );

        loggerService.displayErrorLog(errorResponse.toString());

        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(WeatherApiRequestException exception){
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                exception.getMessage(),
                System.currentTimeMillis()
        );

        loggerService.displayErrorLog(errorResponse.toString());

        return new ResponseEntity<>(errorResponse,HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception exception){
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                exception.getMessage(),
                System.currentTimeMillis()
        );

        loggerService.displayErrorLog(errorResponse.toString());

        return ResponseEntity.internalServerError().body(errorResponse);
    }
}
