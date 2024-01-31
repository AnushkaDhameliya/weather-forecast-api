package com.weatherforecast.logger;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface LoggerService {

    void displayReq(HttpServletRequest request);

    void displayResp(HttpServletRequest request, HttpServletResponse response);

    void displayLog(String logString);
    void displayErrorLog(String errorLogString);
}
