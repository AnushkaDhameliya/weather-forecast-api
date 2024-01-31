package com.weatherforecast.controller;

import com.weatherforecast.entity.Location;
import com.weatherforecast.exception.WebServiceDataNotFoundException;
import com.weatherforecast.exception.WebServiceDataNotValidException;
import com.weatherforecast.exception.WebServiceException;
import com.weatherforecast.logger.LoggerService;
import com.weatherforecast.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @Autowired
    private Environment environment;

    @Autowired
    private LoggerService loggerService;

    @GetMapping("/{locationId}")
    public ResponseEntity<Location> getLocationById(@PathVariable long locationId) {
        validationCheckForLocationId(locationId);
        String cacheControlDays = environment.getProperty("api-cache-days");
        return ResponseEntity
                .ok()
                .cacheControl(CacheControl.maxAge(Long.parseLong(cacheControlDays), TimeUnit.DAYS))
                .body(locationService.getLocationById(locationId));
    }

    @GetMapping
    public ResponseEntity<List<Location>> getAllLocations() {
        String cacheControlDays = environment.getProperty("api-cache-days");
        return ResponseEntity
                .ok()
                .cacheControl(CacheControl.maxAge(Long.parseLong(cacheControlDays), TimeUnit.DAYS))
                .body(locationService.getAllLocations());
    }

    @PostMapping
    public ResponseEntity<Location> createLocation(@RequestBody Location location) {
        validationCheckForLocation(location);
        String cacheControlDays = environment.getProperty("api-cache-days");
        return ResponseEntity
                .ok()
                .cacheControl(CacheControl.maxAge(Long.parseLong(cacheControlDays), TimeUnit.DAYS))
                .body(locationService.createLocation(location));
    }

    @PutMapping("/{locationId}")
    public ResponseEntity<Location> updateLocation(@PathVariable long locationId, @RequestBody Location location) {
        String cacheControlDays = environment.getProperty("api-cache-days");
        location.setLocationId(locationId);
        validationCheckForLocation(location);
        return ResponseEntity
                .ok()
                .cacheControl(CacheControl.maxAge(Long.parseLong(cacheControlDays), TimeUnit.DAYS))
                .body(locationService.updateLocation(location));
    }

    @DeleteMapping("/{locationId}")
    public HttpStatus deleteLocation(@PathVariable long locationId) {
        validationCheckForLocationId(locationId);
        locationService.deleteLocation(locationId);
        return HttpStatus.OK;
    }

    private void validationCheckForLocation(Location location){
        StringBuilder errorString = new StringBuilder();
        if(location.getLocationId() < 0){
            errorString.append("Invalid locationId. ");
        }
        if(!location.getName().matches("^[a-zA-Z\\s]+$")){
            errorString.append("Invalid location name. ");
        }
        if(location.getLatitude() < -90 || location.getLatitude() > 90){
            errorString.append("Invalid latitude co-ordinates. ");
        }
        if(location.getLongitude() < -180 || location.getLongitude() > 180){
            errorString.append("Invalid longitude co-ordinates. ");
        }

        if(!errorString.isEmpty()){
            throw new WebServiceDataNotValidException(errorString.toString());
        }
    }

    private void validationCheckForLocationId(long locationId){
        StringBuilder errorString = new StringBuilder();
        if(locationId <= 0){
            errorString.append("Invalid locationId.");
        }

        if(!errorString.isEmpty()){
            throw new WebServiceDataNotValidException(errorString.toString());
        }
    }
}
