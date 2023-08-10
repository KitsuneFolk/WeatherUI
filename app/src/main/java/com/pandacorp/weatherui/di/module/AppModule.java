package com.pandacorp.weatherui.di.module;

import android.content.Context;

import com.pandacorp.weatherui.data.remote.WeatherApiService;
import com.pandacorp.weatherui.data.repository.WeatherRepositoryImpl;
import com.pandacorp.weatherui.domain.repository.WeatherRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    @Provides
    WeatherRepository providesWeatherRepositoryImpl(Context context, WeatherApiService weatherApiService) {
        return new WeatherRepositoryImpl(context.getAssets(), weatherApiService);
    }
}