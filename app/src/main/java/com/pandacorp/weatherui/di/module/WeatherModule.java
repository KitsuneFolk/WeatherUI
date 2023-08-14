package com.pandacorp.weatherui.di.module;

import android.content.Context;

import com.pandacorp.weatherui.data.remote.WeatherApiService;
import com.pandacorp.weatherui.data.repository.WeatherRepositoryImpl;
import com.pandacorp.weatherui.domain.repository.WeatherRepository;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class WeatherModule {

    @Provides
    @Named("weatherRetrofit")
    public static Retrofit provideWeatherRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    public static WeatherApiService provideWeatherApiService(@Named("weatherRetrofit") Retrofit retrofit) {
        return retrofit.create(WeatherApiService.class);
    }

    @Provides
    public static WeatherRepository provideWeatherRepository(Context context, WeatherApiService weatherApiService) {
        return new WeatherRepositoryImpl(context.getAssets(), weatherApiService);
    }
}