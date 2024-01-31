# weather-forecast-api

Third party source : https://www.weatherapi.com/

API endpoints:

/locations                      (GET) => get all locations 

/locations                      (POST) => create new location 

/locations/{locationId}         (GET) => get location by locationId

/locations/{locationId}         (PUT) => update location by locationId

/locations/{locationId}         (DELETE) => delete location by locationId

/weather/{locationId}           (GET) => get current weather forecast data for locationId 

/history/{locationId}/{days}    (GET) => get histotical weather forecast data for locationId from current date to last mentioned days(last 0 t0 30)
