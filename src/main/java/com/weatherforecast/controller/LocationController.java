package com.weatherforecast.controller;

import com.weatherforecast.entity.Location;
import com.weatherforecast.exception.WebServiceDataNotFoundException;
import com.weatherforecast.exception.WebServiceDataNotValidException;
import com.weatherforecast.exception.WebServiceException;
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

    @GetMapping("/{locationId}")
    public ResponseEntity<Location> getLocationById(@PathVariable long locationId) throws Exception {
        try{
            if(locationId <= 0){
                throw new WebServiceDataNotValidException("Invalid locationId.");
            }
            String cacheControlDays = environment.getProperty("api-cache-days");
            return ResponseEntity
                    .ok()
                    .cacheControl(CacheControl.maxAge(Long.parseLong(cacheControlDays), TimeUnit.DAYS))
                    .body(locationService.getLocationById(locationId));
        }
        catch (WebServiceDataNotFoundException ex){
            throw new WebServiceDataNotFoundException(ex.getMessage());
        }
        catch (WebServiceException ex){
            throw new WebServiceException(ex.getMessage());
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Location>> getAllLocations() throws Exception {
        try{
            String cacheControlDays = environment.getProperty("api-cache-days");
            return ResponseEntity
                    .ok()
                    .cacheControl(CacheControl.maxAge(Long.parseLong(cacheControlDays), TimeUnit.DAYS))
                    .body(locationService.getAllLocations());
        }
        catch (WebServiceException ex){
            throw new WebServiceException(ex.getMessage());
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Location> createLocation(@RequestBody Location location) throws Exception {
        try{
            String errorString = validationCheck(location);
            if(!errorString.isEmpty()){
                throw new WebServiceDataNotValidException(errorString);
            }
            String cacheControlDays = environment.getProperty("api-cache-days");
            return ResponseEntity
                    .ok()
                    .cacheControl(CacheControl.maxAge(Long.parseLong(cacheControlDays), TimeUnit.DAYS))
                    .body(locationService.createLocation(location));
        }
        catch (WebServiceException ex){
            throw new WebServiceException(ex.getMessage());
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @PutMapping("/{locationId}")
    public ResponseEntity<Location> updateLocation(@PathVariable long locationId, @RequestBody Location location) throws Exception {
        try{
            String cacheControlDays = environment.getProperty("api-cache-days");
            location.setLocationId(locationId);
            String errorString = validationCheck(location);
            if(!errorString.isEmpty()){
                throw new WebServiceDataNotValidException(errorString);
            }
            return ResponseEntity
                    .ok()
                    .cacheControl(CacheControl.maxAge(Long.parseLong(cacheControlDays), TimeUnit.DAYS))
                    .body(locationService.updateLocation(location));
        }
        catch (WebServiceException ex){
            throw new WebServiceException(ex.getMessage());
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @DeleteMapping("/{locationId}")
    public HttpStatus deleteLocation(@PathVariable long locationId) throws Exception {
        try{
            if(locationId <= 0){
                throw new WebServiceDataNotValidException("Invalid locationId.");
            }
            locationService.deleteLocation(locationId);
            return HttpStatus.OK;
        }
        catch (WebServiceException ex){
            throw new WebServiceException(ex.getMessage());
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    private String validationCheck(Location location){
        StringBuilder errorString = new StringBuilder();
        if(location.getLocationId() < 0){
            errorString.append("Invalid locationId. ");
        }
        else if(!location.getName().matches("^[a-zA-Z\\s]+$")){
            errorString.append("Invalid location name. ");
        }
        else if(location.getLatitude() < -90 || location.getLatitude() > 90){
            errorString.append("Invalid latitude co-ordinates. ");
        }
        else if(location.getLongitude() < -180 || location.getLongitude() > 180){
            errorString.append("Invalid longitude co-ordinates. ");
        }

        return errorString.toString();
    }
}
