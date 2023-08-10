package com.pandacorp.weatherui.di.component;

import android.app.Application;

import com.pandacorp.weatherui.di.App;
import com.pandacorp.weatherui.di.module.AppModule;
import com.pandacorp.weatherui.di.module.InjectorModule;
import com.pandacorp.weatherui.di.module.NetworkModule;
import com.pandacorp.weatherui.presentation.screens.MainScreen;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

@Singleton
@Component(modules = {AndroidInjectionModule.class, InjectorModule.class, NetworkModule.class, AppModule.class})
public interface AppComponent extends AndroidInjector<App> {
    void inject(MainScreen mainScreen);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}