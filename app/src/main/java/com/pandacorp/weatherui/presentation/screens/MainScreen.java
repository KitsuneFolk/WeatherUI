package com.pandacorp.weatherui.presentation.screens;

import static androidx.navigation.fragment.FragmentKt.findNavController;

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

import com.google.android.material.snackbar.Snackbar;
import com.pandacorp.weatherui.R;
import com.pandacorp.weatherui.databinding.ScreenMainBinding;
import com.pandacorp.weatherui.domain.model.WeatherItem;
import com.pandacorp.weatherui.presentation.WeatherPresenter;
import com.pandacorp.weatherui.presentation.WeatherView;
import com.pandacorp.weatherui.presentation.utils.PreferenceHandler;

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

        initViews();

        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
        weatherPresenter.onDestroy();
    }

    @Override
    public void displayWeather(WeatherItem weatherItem) {
        String currentTemperature = String.valueOf(weatherItem.getMain().getTemp());
        binding.textView.setText(currentTemperature);
        Snackbar.make(binding.textView, "Success", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void displayError(String errorMessage) {
        Snackbar.make(binding.textView, "Error: " + errorMessage, Snackbar.LENGTH_SHORT).show();
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
        weatherPresenter.setWeatherView(this);
        weatherPresenter.getWeather(51.5074, -0.1278, PreferenceHandler.getCurrentLanguageKey(requireContext()));
    }
}