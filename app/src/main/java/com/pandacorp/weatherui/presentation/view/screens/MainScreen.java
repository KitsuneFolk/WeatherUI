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
import com.pandacorp.weatherui.domain.model.CurrentWeather;
import com.pandacorp.weatherui.presentation.model.WeatherModel;
import com.pandacorp.weatherui.presentation.presenter.MainPresenter;
import com.pandacorp.weatherui.presentation.utils.PreferenceHandler;
import com.pandacorp.weatherui.presentation.utils.TextFormat;
import com.pandacorp.weatherui.presentation.view.WeatherView;

import java.util.Map;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class MainScreen extends DaggerFragment implements WeatherView {
    private ScreenMainBinding binding;
    private NavController navController;

    @Inject
    MainPresenter mainPresenter;

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
        mainPresenter.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        if (mainPresenter != null) {
            mainPresenter.onDestroy();
            mainPresenter = null;
        }
        binding = null;
        super.onDestroy();
    }

    @Override
    public void displayWeather(WeatherModel weatherModel) {
        assert weatherModel.getWeatherItem() != null;
        CurrentWeather currentWeather = weatherModel.getWeatherItem().getCurrentWeather();
        String unitMark = "ᶜ";
        String currentTemperature = currentWeather.getMain().getTemp() + "°" + unitMark;
        String feelsLike = requireContext().getString(R.string.feelsLike) + " " + currentWeather.getMain().getFeelsLike() + "°" + unitMark;
        String humidity = requireContext().getString(R.string.humidity) + " " + currentWeather.getMain().getHumidity() + "%";
        var currentLocation = weatherModel.getWeatherItem().getLocations().get(0);
        if (currentLocation != null) {
            Map<String, String> locationNames = currentLocation.getLocal_names();
            String location = locationNames.get(PreferenceHandler.getCurrentLanguageKey(requireContext()));
            binding.locationText.setText(location);
        }
        Weather weather = currentWeather.getWeather().get(0);
        if (weather != null) {
            String description = weather.getDescription();
            // Capitalize the first letter
            description = TextFormat.capitalizeFirstLetter(description);
            binding.descriptionText.setText(description);
        }
        binding.temperatureText.setText(currentTemperature);
        binding.feelsLikeText.setText(feelsLike);
        binding.humidityText.setText(humidity);
    }

    @Override
    public void displayError(String errorMessage) {
        Snackbar.make(binding.getRoot(), errorMessage, Snackbar.LENGTH_LONG).setTextColor(Color.WHITE).show();
    }

    private void restoreInstanceState(Bundle savedInstanceState) {
        mainPresenter.setWeatherView(this);
        if (savedInstanceState != null) {
            mainPresenter.onRestoreInstanceState(savedInstanceState);
        }
    }

    private void getLocation() {
        if (!mainPresenter.isLocationSet()) {
            // No location, retrieve using Locus
            Locus.INSTANCE.getCurrentLocation(requireContext(), result -> {
            Location location = result.getLocation();
            if (location != null) {
                mainPresenter.setLocation(location.getLatitude(), location.getLongitude());
                mainPresenter.getWeather(PreferenceHandler.getCurrentLanguageKey(requireContext()));
            }
            return null;
            });
        } else {
            // There might be a location retrieved from SharedPreference
            mainPresenter.getWeather(PreferenceHandler.getCurrentLanguageKey(requireContext()));
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