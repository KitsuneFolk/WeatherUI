package com.pandacorp.weatherui.domain.repository;

import androidx.annotation.Nullable;
import androidx.core.util.Pair;

public interface PreferencesRepository {
    void saveLocation(double latitude, double longitude);

    @Nullable
    Pair<Double, Double> getLocation();
}
