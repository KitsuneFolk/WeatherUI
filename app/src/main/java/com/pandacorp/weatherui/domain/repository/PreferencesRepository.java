package com.pandacorp.weatherui.domain.repository;

import androidx.core.util.Pair;

public interface PreferencesRepository {
    void saveLocation(double latitude, double longitude);

    boolean isNearBy(double latitude, double longitude);

    Pair<Double, Double> getLocation();
}
