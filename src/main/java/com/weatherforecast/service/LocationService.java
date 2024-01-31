package com.weatherforecast.service;


import com.weatherforecast.entity.Location;

import java.util.List;

public interface LocationService {

    public List<Location> getAllLocations();

    public Location getLocationById(long id);

    public Location createLocation(Location location);

    public Location updateLocation(Location location);

    public void deleteLocation(long id);
}
