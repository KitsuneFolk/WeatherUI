package com.pandacorp.weatherui.presentation.view.screens;

import static androidx.navigation.fragment.FragmentKt.findNavController;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.view.MenuProvider;
import androidx.navigation.NavController;

import com.birjuvachhani.locus.Locus;
import com.google.android.material.snackbar.Snackbar;
import com.pandacorp.weatherui.R;
import com.pandacorp.weatherui.databinding.ScreenMainBinding;
import com.pandacorp.weatherui.domain.model.Weather;
import com.pandacorp.weatherui.domain.model.WeatherItem;
import com.pandacorp.weatherui.presentation.model.WeatherModel;
import com.pandacorp.weatherui.presentation.presenter.WeatherPresenter;
import com.pandacorp.weatherui.presentation.utils.PreferenceHandler;
import com.pandacorp.weatherui.presentation.view.WeatherView;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class MainScreen extends DaggerFragment implements WeatherView {
    private ScreenMainBinding binding;
    private NavController navController;

    @Inject
    WeatherPresenter weatherPresenter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = ScreenMainBinding.inflate(getLayoutInflater());

        restoreInstanceState(savedInstanceState);

        getLocation();

        initViews();

        return binding.getRoot();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        weatherPresenter.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        if (weatherPresenter != null) {
            weatherPresenter.onDestroy();
            weatherPresenter = null;
        }
        binding = null;
        super.onDestroy();
    }

    @Override
    public void displayWeather(WeatherModel weatherModel) {
        assert weatherModel.getWeatherItem() != null;
        WeatherItem weatherItem = weatherModel.getWeatherItem();
        String unitMark = "ᶜ";
        String currentTemperature = weatherItem.getMain().getTemp() + "°" + unitMark;
        String location = ""; //TODO:
        String feelsLike = requireContext().getString(R.string.feelsLike) + " " + weatherItem.getMain().getFeelsLike() + "°" + unitMark;
        String humidity = requireContext().getString(R.string.humidity) + " " + weatherItem.getMain().getHumidity() + "%";
        binding.locationText.setText(location);
        binding.temperatureText.setText(currentTemperature);
        Weather weather = weatherItem.getWeather().get(0);
        if (weather != null) {
            binding.descriptionText.setText(weather.getDescription());
        }
        binding.feelsLikeText.setText(feelsLike);
        binding.humidityText.setText(humidity);
    }

    @Override
    public void displayError(String errorMessage) {
        Snackbar.make(binding.getRoot(), errorMessage, Snackbar.LENGTH_LONG).setTextColor(Color.WHITE).show();
    }

    private void restoreInstanceState(Bundle savedInstanceState) {
        weatherPresenter.setWeatherView(this);
        if (savedInstanceState != null) {
            weatherPresenter.onRestoreInstanceState(savedInstanceState);
        }
    }

    private void getLocation() {
        if (!weatherPresenter.isLocationSet()) {
            Locus.INSTANCE.getCurrentLocation(requireContext(), result -> {
            Location location = result.getLocation();
            if (location != null) {
                weatherPresenter.setLocation(location.getLatitude(), location.getLongitude());
                weatherPresenter.getWeather(PreferenceHandler.getCurrentLanguageKey(requireContext()));
            }
            return null;
            });
        }
    }

    private void initViews() {
        navController = findNavController(this);
        binding.toolbarInclude.toolbar.setTitle(R.string.app_name);
        binding.toolbarInclude.toolbar.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.menu, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.settings) {
                    navController.navigate(R.id.nav_settings_screen);
                    return true;
                }
                return false;
            }
        }, getViewLifecycleOwner());
    }
}