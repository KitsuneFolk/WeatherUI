package com.pandacorp.weatherui.domain.repository;

import com.pandacorp.weatherui.domain.model.CurrentWeather;

import io.reactivex.rxjava3.core.Observable;

public interface WeatherRepository {
    Observable<CurrentWeather> getWeather(Double latitude, Double longitude, String excludeParts, String units, String language);
}
