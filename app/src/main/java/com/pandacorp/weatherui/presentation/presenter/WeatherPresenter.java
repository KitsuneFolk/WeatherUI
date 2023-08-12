package com.pandacorp.weatherui.presentation.presenter;

import android.os.Bundle;

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
    private WeatherModel weatherModel = new WeatherModel();

    @Inject
    public WeatherPresenter(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    public void setWeatherView(WeatherView weatherView) {
        this.weatherView = weatherView;
    }

    public void getWeather(String language) {
        getWeather(weatherModel.getLatitude(), weatherModel.getLongitude(), language);
    }

    public void getWeather(Double latitude, Double longitude, String language) {
        String units = "metric"; // Celsius
        String excludeParts = "minutely, hourly, daily, alerts";
        if (weatherModel.getWeatherItem() != null) return;
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
                                weatherModel.setErrorMessage(error.getLocalizedMessage());
                                weatherView.displayError(error.getLocalizedMessage());
                            }
                        });
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(Constants.SAVED_WEATHER_MODEL, weatherModel);
    }

    public void onRestoreInstanceState(Bundle outState) {
        WeatherModel bundleModel = BundleCompatUtils.getSerializableCompat(outState, Constants.SAVED_WEATHER_MODEL, WeatherModel.class);
        if (bundleModel == null) return;
        weatherModel = bundleModel;
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
        weatherSubscriber = null;
        weatherRepository = null;
    }

    public void setLocation(Double latitude, Double longitude) {
        assert weatherModel != null;
        weatherModel.setLatitude(latitude);
        weatherModel.setLongitude(longitude);
    }

    public Boolean isLocationSet() {
        if (weatherModel == null) return false;
        return weatherModel.getLatitude() != null && weatherModel.getLongitude() != null;
    }
}