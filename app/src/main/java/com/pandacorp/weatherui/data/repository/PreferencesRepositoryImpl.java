package com.pandacorp.weatherui.data.repository;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.preference.PreferenceManager;

import com.pandacorp.weatherui.domain.repository.PreferencesRepository;

public class PreferencesRepositoryImpl implements PreferencesRepository {
    private final static String LATITUDE = "LATITUDE";
    private final static String LONGITUDE = "LONGITUDE";
    private final static float ERROR = -10000F;

    private final SharedPreferences sp;
    @Nullable
    private Pair<Double, Double> currentLocation;

    public PreferencesRepositoryImpl(@NonNull Context context) {
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        var location = retrieveLocation();
        if (location != null) {
            currentLocation = location;
        }
    }

    @Nullable
    @Override
    public Pair<Double, Double> getLocation() {
        return currentLocation;
    }

    @Override
    public void saveLocation(double latitude, double longitude) {
        currentLocation = Pair.create(latitude, longitude);
        SharedPreferences.Editor editor = sp.edit();
        editor.putFloat(LATITUDE, (float) latitude);
        editor.putFloat(LONGITUDE, (float) longitude);
        editor.apply();
    }

    private Pair<Double, Double> retrieveLocation() {
        float latitude = sp.getFloat(LATITUDE, ERROR);
        float longitude = sp.getFloat(LONGITUDE, ERROR);
        Pair<Double, Double> pair;
        if (latitude == ERROR || longitude == ERROR) {
            pair = null;
        } else {
            pair = Pair.create((double) latitude, (double) longitude);
        }
        currentLocation = pair;
        return pair;
    }

}
