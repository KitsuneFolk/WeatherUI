package com.pandacorp.weatherui.data.remote;

import com.pandacorp.weatherui.domain.model.Location;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LocationApiService {
    @GET("reverse")
    Observable<List<Location>> getLocation(
            @Query("lat") double latitude,
            @Query("lon") double longitude,
            @Query("limit") int limit,
            @Query("appid") String apiKey
    );
}
