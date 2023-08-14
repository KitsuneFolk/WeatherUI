package com.pandacorp.weatherui.domain.model;

import java.io.Serializable;
import java.util.List;

public class WeatherItem implements Serializable {
    private List<Location> locations;
    private CurrentWeather currentWeather;

    public WeatherItem(List<Location> location, CurrentWeather currentWeather) {
        this.locations = location;
        this.currentWeather = currentWeather;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public CurrentWeather getCurrentWeather() {
        return currentWeather;
    }

    public void setCurrentWeather(CurrentWeather currentWeather) {
        this.currentWeather = currentWeather;
    }
}