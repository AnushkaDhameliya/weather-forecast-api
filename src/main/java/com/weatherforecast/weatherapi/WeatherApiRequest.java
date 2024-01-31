package com.weatherforecast.weatherapi;

import com.weatherforecast.entity.Location;

public interface WeatherApiRequest {

    Object getForecastForLocation(Location location);

    Object getHistoricalForecastForLocationAndDate(Location location, int days);
}
