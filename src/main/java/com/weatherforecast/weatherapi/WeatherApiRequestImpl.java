package com.weatherforecast.weatherapi;

import com.weatherforecast.entity.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;

@Service
public class WeatherApiRequestImpl implements WeatherApiRequest{

    @Autowired
    private Environment environment;

    @Autowired
    private WebClient webclientBuilder;

    @Override
    public Object getForecastForLocation(Location location) {
        try{
            String apiPath = environment.getProperty("weather-forecast-current-data-api");
            String apiKey = environment.getProperty("weather-forecast-api-key");
            return webclientBuilder
                    .get()
                    .uri(uriBuilder -> uriBuilder.path(apiPath)
                            .queryParam("key",apiKey)
                            .queryParam("q",location.getLatitude()+","+ location.getLongitude())
                            .build())
                    .retrieve()
                    .bodyToMono(Object.class)
                    .block();
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Object getHistoricalForecastForLocationAndDate(Location location, int days) {
        try{
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            String current_date = formatter.format(date);
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE,-days);
            String end_date = formatter.format(calendar.getTime());
            String apiPath = environment.getProperty("weather-forecast-history-data-api");
            String apiKey = environment.getProperty("weather-forecast-api-key");
            return webclientBuilder
                    .get()
                    .uri(uriBuilder -> uriBuilder.path(apiPath)
                            .queryParam("key",apiKey)
                            .queryParam("q",location.getLatitude()+","+ location.getLongitude())
                            .queryParam("dt",end_date)
                            .queryParam("end_dt",current_date)
                            .build())
                    .retrieve()
                    .bodyToMono(Object.class)
                    .block();
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
