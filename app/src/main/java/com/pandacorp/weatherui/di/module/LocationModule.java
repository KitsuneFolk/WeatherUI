package com.pandacorp.weatherui.di.module;

import android.content.Context;

import com.pandacorp.weatherui.data.remote.LocationApiService;
import com.pandacorp.weatherui.data.repository.LocationRepositoryImpl;
import com.pandacorp.weatherui.domain.repository.LocationRepository;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class LocationModule {

    @Provides
    @Named("locationRetrofit")
    public static Retrofit provideLocationRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/geo/1.0/")
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    public static LocationApiService provideLocationApiService(@Named("locationRetrofit") Retrofit retrofit) {
        return retrofit.create(LocationApiService.class);
    }

    @Provides
    public static LocationRepository provideLocationRepository(Context context, LocationApiService weatherApiService) {
        return new LocationRepositoryImpl(context.getAssets(), weatherApiService);
    }
}