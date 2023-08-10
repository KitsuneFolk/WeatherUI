package com.pandacorp.weatherui.presentation.view;

import com.pandacorp.weatherui.presentation.model.WeatherModel;

public interface WeatherView {
    void displayWeather(WeatherModel weatherModel);
    void displayError(String errorMessage);
}