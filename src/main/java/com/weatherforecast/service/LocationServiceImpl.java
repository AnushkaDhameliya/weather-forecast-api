package com.weatherforecast.service;


import com.weatherforecast.entity.Location;
import com.weatherforecast.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationServiceImpl implements LocationService{

    @Autowired
    private LocationRepository locationRepository;

//    private static final org.apache.logging.log4j.Logger log =
//            org.apache.logging.log4j.LogManager.getLogger(LocationService.class);

    @Override
    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    @Override
    public Location getLocationById(long id) {
        Optional<Location> location = locationRepository.findById(id);
        return location.orElse(null);
    }

    @Override
    public Location createLocation(Location location) {
        return locationRepository.save(location);
    }

    @Override
    public Location updateLocation(Location location) {
        Optional<Location> existing = locationRepository.findById(location.getLocationId());
        if(existing.isPresent())
            return locationRepository.save(location);
        else
            return null;
    }

    @Override
    public void deleteLocation(long id) {
        try{
            locationRepository.deleteById(id);
        }catch (Exception e){
            //log.error("Exception Occurred: " + e.getMessage());
        }
    }
}
