package com.pandacorp.weatherui.data.repository;

import android.content.res.AssetManager;

import com.pandacorp.weatherui.data.remote.WeatherApiService;
import com.pandacorp.weatherui.domain.model.CurrentWeather;
import com.pandacorp.weatherui.domain.repository.WeatherRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

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

    @Override
    public Observable<CurrentWeather> getWeather(Double latitude, Double longitude, String excludeParts, String units, String language) {
        return weatherApiService.getWeather(latitude, longitude, excludeParts, apiKey, units, language);
    }
}