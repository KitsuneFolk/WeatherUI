package com.pandacorp.weatherui.presentation.view.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import com.pandacorp.weatherui.databinding.ActivityMainBinding;
import com.pandacorp.weatherui.presentation.utils.PreferenceHandler;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen.installSplashScreen(this);
        PreferenceHandler.setLanguage(this);
        super.onCreate(savedInstanceState);
        PreferenceHandler.setTheme(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}