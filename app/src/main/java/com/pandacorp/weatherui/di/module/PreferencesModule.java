package com.pandacorp.weatherui.di.module;

import android.content.Context;

import com.pandacorp.weatherui.data.repository.PreferencesRepositoryImpl;
import com.pandacorp.weatherui.domain.repository.PreferencesRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class PreferencesModule {

    @Singleton
    @Provides
    public static PreferencesRepository providePreferencesRepository(Context context) {
        return new PreferencesRepositoryImpl(context);
    }
}