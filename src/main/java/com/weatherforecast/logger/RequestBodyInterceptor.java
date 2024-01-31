package com.weatherforecast.logger;

import com.weatherforecast.exception.WebServiceExceptionHandler;
import com.weatherforecast.logger.LoggerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.lang.reflect.Type;

@ControllerAdvice
public class RequestBodyInterceptor extends RequestBodyAdviceAdapter {

    @Autowired
    private LoggerService loggerService;

    @Autowired
    private HttpServletRequest request;

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        loggerService.displayReq(request);
        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
    }
    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }
}
