package com.pandacorp.weatherui.data.repository;

import android.content.res.AssetManager;

import com.pandacorp.weatherui.data.remote.LocationApiService;
import com.pandacorp.weatherui.domain.model.Location;
import com.pandacorp.weatherui.domain.repository.LocationRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class LocationRepositoryImpl implements LocationRepository {
    private final LocationApiService locationApiService;
    private static String apiKey;

    @Inject
    public LocationRepositoryImpl(AssetManager assetManager, LocationApiService locationApiService) {
        this.locationApiService = locationApiService;

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
    public Observable<List<Location>> getLocation(Double latitude, Double longitude, int limit) {
        return locationApiService.getLocation(latitude, longitude, limit, apiKey);
    }

}