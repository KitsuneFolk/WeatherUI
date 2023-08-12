package com.pandacorp.weatherui.presentation.model;

import androidx.annotation.Nullable;

import com.pandacorp.weatherui.domain.model.WeatherItem;

import java.io.Serializable;

public class WeatherModel implements Serializable {
    @Nullable
    private WeatherItem weatherItem;

    private Double latitude;
    private Double longitude;

    private String errorMessage;

    public WeatherModel() {}

    @Nullable
    public WeatherItem getWeatherItem() {
        return weatherItem;
    }

    public void setWeatherItem(@Nullable WeatherItem weatherItem) {
        this.weatherItem = weatherItem;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
