package com.pandacorp.weatherui.presentation;

import com.pandacorp.weatherui.domain.repository.WeatherRepository;

import javax.inject.Inject;

import io.reactivex.rxjava3.disposables.Disposable;

public class WeatherPresenter {
    private WeatherRepository weatherRepository;
    private WeatherView weatherView;
    private Disposable subscriber;

    @Inject
    public WeatherPresenter(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    public void setWeatherView(WeatherView weatherView) {
        this.weatherView = weatherView;
    }

    public void getWeather(Double latitude, Double longitude, String language) {
        String units = "metric"; // Celsius
        String excludeParts = "minutely, hourly, daily, alerts";
        subscriber = weatherRepository.getWeather(latitude, longitude, excludeParts, units, language).subscribe(weatherItem -> {
                    if (weatherView != null) {
                        weatherView.displayWeather(weatherItem);
                    }
                },
                error -> {
                    if (weatherView != null) {
                        error.printStackTrace();
                        weatherView.displayError(error.getMessage());
                    }
                });
    }

    public void onDestroy() {
        subscriber.dispose();
        subscriber = null;
        weatherView = null;
        weatherRepository = null;
    }

}