package com.weatherforecast.logger;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LoggerServiceImpl implements LoggerService{

    Logger logger = LoggerFactory.getLogger("LoggingServiceImpl");

    @Override
    public void displayReq(HttpServletRequest request) {
        StringBuilder reqMessage = new StringBuilder();

        reqMessage.append("REQUEST ");
        reqMessage.append("method = [").append(request.getMethod()).append("]");
        reqMessage.append(" path = [").append(request.getRequestURI()).append("] ");

        logger.info("log Request: {}", reqMessage);
    }

    @Override
    public void displayResp(HttpServletRequest request, HttpServletResponse response) {
        StringBuilder respMessage = new StringBuilder();
        respMessage.append("RESPONSE ");
        respMessage.append(" method = [").append(request.getMethod()).append("]");

        logger.info("logResponse: {}",respMessage);
    }

    @Override
    public void displayLog(String logString) {
        logger.info(logString);
    }

    @Override
    public void displayErrorLog(String errorLogString) {
        logger.error(errorLogString);
    }
}
