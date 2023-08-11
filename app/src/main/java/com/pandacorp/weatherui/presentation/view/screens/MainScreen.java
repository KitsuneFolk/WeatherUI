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

        weatherPresenter.setWeatherView(this);
        if (savedInstanceState != null) {
            weatherPresenter.onRestoreInstanceState(savedInstanceState);
        }
        Locus.INSTANCE.getCurrentLocation(requireContext(),
                result -> {
                    Location location = result.getLocation();
                    if (location != null) {
                        weatherPresenter.setLocation(location);
                        weatherPresenter.getWeather(PreferenceHandler.getCurrentLanguageKey(requireContext()));
                    }
                    return null;
                });

        initViews();

        return binding.getRoot();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        weatherPresenter.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyView() {
        if (weatherPresenter != null) {
            weatherPresenter.onDestroy();
            weatherPresenter = null;
        }
        binding = null;
        super.onDestroyView();
    }

    @Override
    public void displayWeather(WeatherModel weatherModel) {
        String currentTemperature = String.valueOf(weatherModel.getWeatherItem().getMain().getTemp());
        binding.textView.setText(currentTemperature);
    }

    @Override
    public void displayError(String errorMessage) {
        Snackbar.make(binding.textView, errorMessage, Snackbar.LENGTH_LONG).setTextColor(Color.WHITE).show();
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