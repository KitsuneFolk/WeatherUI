package com.pandacorp.weatherui.di.component;

import android.app.Application;

import com.pandacorp.weatherui.di.App;
import com.pandacorp.weatherui.di.module.InjectorModule;
import com.pandacorp.weatherui.di.module.LocationModule;
import com.pandacorp.weatherui.di.module.PreferencesModule;
import com.pandacorp.weatherui.di.module.WeatherModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

@Singleton
@Component(modules = {AndroidInjectionModule.class, InjectorModule.class, LocationModule.class, WeatherModule.class, PreferencesModule.class})
public interface AppComponent extends AndroidInjector<App> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}