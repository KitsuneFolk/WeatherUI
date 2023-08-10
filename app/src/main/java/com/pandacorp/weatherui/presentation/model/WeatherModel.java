package com.pandacorp.weatherui.presentation.model;

import com.pandacorp.weatherui.domain.model.WeatherItem;

import java.io.Serializable;

public class WeatherModel implements Serializable {
    private WeatherItem weatherItem;

    private String errorMessage;

    public WeatherModel() {}

    public WeatherModel(WeatherItem weatherItem, String errorMessage) {
        this.weatherItem = weatherItem;
        this.errorMessage = errorMessage;
    }

    public WeatherItem getWeatherItem() {
        return weatherItem;
    }

    public void setWeatherItem(WeatherItem weatherItem) {
        this.weatherItem = weatherItem;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
