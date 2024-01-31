package com.weatherforecast.controller;

import com.weatherforecast.entity.Location;
import com.weatherforecast.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/locations")
public class LocationController {

//    private static final org.apache.logging.log4j.Logger log =
//            org.apache.logging.log4j.LogManager.getLogger(LocationController.class);

    @Autowired
    private LocationService locationService;

    @GetMapping("/{locationId}")
    public ResponseEntity<Location> getLocationById(@PathVariable long locationId){
        return ResponseEntity.ok().body(locationService.getLocationById(locationId));
    }


    @GetMapping
    public ResponseEntity<List<Location>> getAllLocations(){
        return ResponseEntity.ok().body(locationService.getAllLocations());
    }

    @PostMapping
    public ResponseEntity<Location> createLocation(@RequestBody Location location){
        return ResponseEntity.ok().body(locationService.createLocation(location));
    }

    @PutMapping("/{locationId}")
    public ResponseEntity<Location> updateLocation(@PathVariable long locationId, @RequestBody Location location){
        location.setLocationId(locationId);
        return ResponseEntity.ok().body(locationService.updateLocation(location));
    }

    @DeleteMapping("/{locationId}")
    public HttpStatus deleteLocation(@PathVariable long locationId){
        locationService.deleteLocation(locationId);
        return HttpStatus.OK;
    }
}
