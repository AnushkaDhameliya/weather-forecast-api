package com.weatherforecast.controller;

import com.weatherforecast.entity.Location;
import com.weatherforecast.exception.WeatherApiRequestException;
import com.weatherforecast.exception.WebServiceDataNotFoundException;
import com.weatherforecast.exception.WebServiceDataNotValidException;
import com.weatherforecast.service.LocationService;
import com.weatherforecast.weatherapi.WeatherApiRequest;
import com.weatherforecast.weatherapi.WeatherApiRequestParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/weatherforecast")
public class WeatherforecastController {

    @Autowired
    private WeatherApiRequest weatherApiRequest;

    @Autowired
    private LocationService locationService;

    @Autowired
    private Environment environment;

    @GetMapping("/{locationId}")
    public ResponseEntity<Object> getCurrentForecastData(@PathVariable long locationId) throws Exception {
        try{
            String cacheControlDays = environment.getProperty("api-cache-days");
            Location location = locationService.getLocationById(locationId);
            return ResponseEntity
                    .ok()
                    .cacheControl(CacheControl.maxAge(Long.parseLong(cacheControlDays), TimeUnit.DAYS))
                    .body(weatherApiRequest.getForecastForLocation(location));
        }
        catch (WebServiceDataNotFoundException ex){
            throw new WebServiceDataNotFoundException(ex.getMessage());
        }
        catch (WeatherApiRequestException ex){
            throw new WeatherApiRequestException(ex.getMessage());
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @GetMapping("/history/{locationId}/{days}")
    public ResponseEntity<Object> getHistoryForecastData(@PathVariable long locationId, @PathVariable int days) throws Exception {
        try{
            String cacheControlDays = environment.getProperty("api-cache-days");
            if (days <= 30 && days >= 0) {
                Location location = locationService.getLocationById(locationId);
                if(location != null)
                    return ResponseEntity
                            .ok()
                            .cacheControl(CacheControl.maxAge(Long.parseLong(cacheControlDays), TimeUnit.DAYS))
                            .body(weatherApiRequest.getHistoricalForecastForLocationAndDate(location,days));
            }
            throw new WebServiceDataNotValidException("days should be between 0 and 30.");
        }catch (WebServiceDataNotFoundException ex){
            throw new WebServiceDataNotFoundException(ex.getMessage());
        }
        catch (WeatherApiRequestException ex){
            throw new WeatherApiRequestException(ex.getMessage());
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
