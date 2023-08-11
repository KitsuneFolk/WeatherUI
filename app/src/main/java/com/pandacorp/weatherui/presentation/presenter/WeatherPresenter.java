package com.pandacorp.weatherui.presentation.presenter;

import android.location.Location;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.pandacorp.weatherui.domain.repository.WeatherRepository;
import com.pandacorp.weatherui.presentation.model.WeatherModel;
import com.pandacorp.weatherui.presentation.utils.BundleCompatUtils;
import com.pandacorp.weatherui.presentation.utils.Constants;
import com.pandacorp.weatherui.presentation.view.WeatherView;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class WeatherPresenter {
    private WeatherRepository weatherRepository;
    private WeatherView weatherView;
    private Disposable weatherSubscriber;
    @Nullable
    private WeatherModel weatherModel;
    private Location location;

    @Inject
    public WeatherPresenter(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    public void setWeatherView(WeatherView weatherView) {
        this.weatherView = weatherView;
    }

    public void getWeather(String language) {
        getWeather(location.getLatitude(), location.getLongitude(), language);
    }

    public void getWeather(Double latitude, Double longitude, String language) {
        String units = "metric"; // Celsius
        String excludeParts = "minutely, hourly, daily, alerts";
        if (weatherModel != null) return;
        weatherModel = new WeatherModel();
        weatherSubscriber = weatherRepository.getWeather(latitude, longitude, excludeParts, units, language)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(weatherItem -> {
                            weatherModel.setWeatherItem(weatherItem);
                            weatherModel.setErrorMessage(null);
                            if (weatherView != null) {
                                weatherView.displayWeather(weatherModel);
                            }
                        },
                        error -> {
                            if (weatherView != null) {
                                error.printStackTrace();
                                weatherModel.setErrorMessage(error.getMessage());
                                weatherView.displayError(error.getMessage());
                            }
                        });
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(Constants.SAVED_WEATHER_MODEL, weatherModel);
    }

    public void onRestoreInstanceState(Bundle outState) {
        weatherModel = BundleCompatUtils.getSerializableCompat(outState, Constants.SAVED_WEATHER_MODEL, WeatherModel.class);
        if (weatherModel == null) return;
        if (weatherModel.getErrorMessage() == null) {
            weatherView.displayWeather(weatherModel);
        } else {
            weatherView.displayError(weatherModel.getErrorMessage());
        }
    }

    public void onDestroy() {
        if (weatherSubscriber != null && !weatherSubscriber.isDisposed()) {
            weatherSubscriber.dispose();
        }
        weatherView = null;
        weatherModel = null;
        location = null;
        weatherSubscriber = null;
        weatherRepository = null;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}