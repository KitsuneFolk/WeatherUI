package com.pandacorp.weatherui.domain.repository;

import com.pandacorp.weatherui.domain.model.WeatherItem;

import io.reactivex.rxjava3.core.Observable;

public interface WeatherRepository {
    Observable<WeatherItem> getWeather(Double latitude, Double longitude, String excludeParts, String units, String language);
}
