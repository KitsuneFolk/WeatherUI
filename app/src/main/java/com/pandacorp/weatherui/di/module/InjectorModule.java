package com.pandacorp.weatherui.di.module;

import android.app.Application;
import android.content.Context;

import com.pandacorp.weatherui.presentation.view.screens.MainScreen;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class InjectorModule {
    @Binds
    abstract Context context(Application application);

    @ContributesAndroidInjector
    abstract MainScreen bindMainScreen();
}