package com.pandacorp.weatherui.presentation;

import com.pandacorp.weatherui.domain.model.WeatherItem;

public interface WeatherView {
    void displayWeather(WeatherItem weatherItem);
    void displayError(String errorMessage);
}