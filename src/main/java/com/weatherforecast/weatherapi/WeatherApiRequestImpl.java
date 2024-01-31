package com.weatherforecast.weatherapi;

import com.weatherforecast.entity.Location;
import com.weatherforecast.exception.WeatherApiRequestException;
import com.weatherforecast.exception.WebServiceDataNotFoundException;
import com.weatherforecast.logger.LoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Logger;

@Service
public class WeatherApiRequestImpl implements WeatherApiRequest{

    @Autowired
    private Environment environment;

    @Autowired
    private WebClient webclientBuilder;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private LoggerService loggerService;

    @Override
    public Object getForecastForLocation(Location location) {
        String apiPath= "";
        try{

            loggerService.displayLog("Weather Forecast External API call for current forecast data started ==>");

            apiPath = environment.getProperty("weather-forecast-current-data-api");
            String apiKey = environment.getProperty("weather-forecast-api-key");
            String finalApiPath = apiPath;
            String timeOut = environment.getProperty("weather-forecast-api-timeout");
            String cordinateQParam = location.getLatitude()+","+ location.getLongitude();

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            String current_date = formatter.format(date);

            HashSet<String> query = new HashSet<>();
            query.add(cordinateQParam);
            query.add(current_date);
            if(cacheManager.get(query) != null){
                Object response = cacheManager.get(query);
                loggerService.displayLog("Data found in Cache. Queried data -> Coordinate Parameters = " + cordinateQParam + " and Date = " + current_date);
                return response;
            }
            else{
                Object response = webclientBuilder
                        .get()
                        .uri(uriBuilder -> uriBuilder.path(finalApiPath)
                                .queryParam("key",apiKey)
                                .queryParam("q",cordinateQParam)
                                .build())
                        .retrieve()
                        .bodyToMono(Object.class)
                        .timeout(Duration.ofSeconds(Long.parseLong(timeOut)))
                        .block();
                loggerService.displayLog("API call made. Queried data -> Coordinate Parameters = " + cordinateQParam + " and Date = " + current_date);
                loggerService.displayLog("Response from API call: " + response.toString());
                cacheManager.put(query,response);
                loggerService.displayLog("Data inserted into cache. ");
                return response;
            }
        }catch (Exception e){
            throw new WeatherApiRequestException("Exception occurred while calling weatherapi.com/"+ apiPath +"api. " + e.getMessage());
        }
        finally {
            loggerService.displayLog("Weather Forecast External API call for current forecast data ended ==>");
        }
    }

    @Override
    public Object getHistoricalForecastForLocationAndDate(Location location, int days) {
        String apiPath="";
        try{
            loggerService.displayLog("Weather Forecast External API call for history forecast data started ==>");
            //calculate dates from days
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            String current_date = formatter.format(date);
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE,-days);
            String end_date = formatter.format(calendar.getTime());

            apiPath = environment.getProperty("weather-forecast-history-data-api");
            String apiKey = environment.getProperty("weather-forecast-api-key");
            String finalApiPath = apiPath;
            String timeOut = environment.getProperty("weather-forecast-api-timeout");

            String cordinateQParam = location.getLatitude()+","+ location.getLongitude();

            loggerService.displayLog("API call made. Queried data -> Coordinate Parameters = " + cordinateQParam + " and start_date = " + current_date + " end_date = " + end_date);

            Object response = webclientBuilder
                    .get()
                    .uri(uriBuilder -> uriBuilder.path(finalApiPath)
                            .queryParam("key",apiKey)
                            .queryParam("q",cordinateQParam)
                            .queryParam("dt",end_date)
                            .queryParam("end_dt",current_date)
                            .build())
                    .retrieve()
                    .bodyToMono(Object.class)
                    .timeout(Duration.ofSeconds(Long.parseLong(timeOut)))
                    .block();
            loggerService.displayLog("Response from API call: " + response.toString());
            return response;
        }catch (Exception e){
            throw new WeatherApiRequestException("Exception occurred while calling weatherapi.com/" + apiPath + " api. " + e.getMessage());
        }
        finally {
            loggerService.displayLog("Weather Forecast External API call for history forecast data ended ==>");
        }
    }
}
