package com.weatherforecast.service;


import com.weatherforecast.entity.Location;
import com.weatherforecast.exception.WebServiceDataNotFoundException;
import com.weatherforecast.exception.WebServiceException;
import com.weatherforecast.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationServiceImpl implements LocationService{

    @Autowired
    private LocationRepository locationRepository;


    @Override
    public List<Location> getAllLocations() {
        try{
            return locationRepository.findAll();
        }
        catch (Exception e){
            throw new WebServiceException("Exception occurred while fetching locations. " + e.getMessage());
        }
    }

    @Override
    public Location getLocationById(long id) {
        try{
            Optional<Location> location = locationRepository.findById(id);
            if(location.isPresent()){
                return location.get();
            }
            throw new WebServiceDataNotFoundException("Location not found with id:" + id);
        }
        catch (Exception e){
            throw new WebServiceException("Exception occurred while fetching location with locationId:" + id + ". "+ e.getMessage());
        }
    }

    @Override
    public Location createLocation(Location location) {
        try{
            location.setLocationId(0L);
            return locationRepository.save(location);
        }
        catch (Exception e){
            throw new WebServiceException("Exception occurred while creating new location with data:" + location.toString() + ". " + e.getMessage());
        }
    }

    @Override
    public Location updateLocation(Location location) {
        try{
            Optional<Location> existing = locationRepository.findById(location.getLocationId());
            if(existing.isPresent())
                return locationRepository.save(location);
            throw new WebServiceDataNotFoundException("Location not found with locationId:" + location.getLocationId());
        }
        catch (Exception e){
            throw new WebServiceException("Exception occurred while updating the location with data:" + location.toString() + ". " + e.getMessage());
        }
    }

    @Override
    public void deleteLocation(long id) {
        try{
            Optional<Location> existing = locationRepository.findById(id);
            if(existing.isPresent())
                locationRepository.deleteById(id);
            else
                throw new WebServiceDataNotFoundException("Location not found with locationId:" + id);
        }catch (Exception e){
            throw new WebServiceException("Exception occurred while deleting the location with locationId:" + id + ". " + e.getMessage());
        }
    }
}
