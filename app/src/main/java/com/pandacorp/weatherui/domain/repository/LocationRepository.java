package com.pandacorp.weatherui.domain.repository;

import com.pandacorp.weatherui.domain.model.Location;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public interface LocationRepository {
    Observable<List<Location>> getLocation(Double latitude, Double longitude, int limit);
}
