package com.pandacorp.weatherui.domain.model;

import java.io.Serializable;
import java.util.List;

public class WeatherItem implements Serializable {
    private final List<Location> locations;
    private final CurrentWeather currentWeather;

    public WeatherItem(List<Location> location, CurrentWeather currentWeather) {
        this.locations = location;
        this.currentWeather = currentWeather;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public CurrentWeather getCurrentWeather() {
        return currentWeather;
    }
}