package com.weatherforecast.controller;

import com.weatherforecast.entity.Location;
import com.weatherforecast.service.LocationService;
import com.weatherforecast.weatherapi.WeatherApiRequest;
import com.weatherforecast.weatherapi.WeatherApiRequestParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/weatherforecast")
public class WeatherforecastController {

    @Autowired
    private WeatherApiRequest weatherApiRequest;

    @Autowired
    private LocationService locationService;

    @GetMapping("/{locationId}")
    public ResponseEntity<Object> getCurrentForecastData(@PathVariable long locationId){
        try{
            Location location = locationService.getLocationById(locationId);
            if(location != null)
                return ResponseEntity.ok().body(weatherApiRequest.getForecastForLocation(location));
        }catch (Exception e){

        }
        return ResponseEntity.badRequest().body("location not found.");
    }

    @GetMapping("/history/{locationId}/{days}")
    public ResponseEntity<Object> getHistoryForecastData(@PathVariable long locationId, @PathVariable int days){
        try{
            if (days <= 30 && days >= 0) {
                Location location = locationService.getLocationById(locationId);
                if(location != null)
                    return ResponseEntity.ok().body(weatherApiRequest.getHistoricalForecastForLocationAndDate(location,days));
            }
            return ResponseEntity.badRequest().body("days should be between 0 and 30.");
        }catch (Exception e){

        }
        return null;
    }
}
