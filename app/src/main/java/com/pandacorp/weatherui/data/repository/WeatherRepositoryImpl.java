package com.pandacorp.weatherui.data.repository;

import android.content.res.AssetManager;

import com.pandacorp.weatherui.data.remote.WeatherApiService;
import com.pandacorp.weatherui.domain.model.WeatherItem;
import com.pandacorp.weatherui.domain.repository.WeatherRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class WeatherRepositoryImpl implements WeatherRepository {
    private final WeatherApiService weatherApiService;
    private static String apiKey;

    @Inject
    public WeatherRepositoryImpl(AssetManager assetManager, WeatherApiService weatherApiService) {
        this.weatherApiService = weatherApiService;

        Properties properties = new Properties();
        try {
            InputStream asset = assetManager.open("API_KEY.properties");
            properties.load(asset);
            apiKey = properties.getProperty("key");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Observable<WeatherItem> getWeather(Double latitude, Double longitude, String excludeParts, String units, String language) {
        return weatherApiService.getWeather(latitude, longitude, excludeParts, apiKey, units, language)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}