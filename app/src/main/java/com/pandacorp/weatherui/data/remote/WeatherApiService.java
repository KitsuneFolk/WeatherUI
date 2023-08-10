package com.pandacorp.weatherui.data.remote;

import com.pandacorp.weatherui.domain.model.WeatherItem;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApiService {
    @GET("weather")
    Observable<WeatherItem> getWeather(
            @Query("lat") double latitude,
            @Query("lon") double longitude,
            @Query("exclude") String excludeParts,
            @Query("appid") String apiKey,
            @Query("units") String units,
            @Query("lang") String language
    );
}
