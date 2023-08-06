package com.pandacorp.weatherui.screens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.pandacorp.weatherui.databinding.ScreenMainBinding;

public class MainScreen extends Fragment {
    private ScreenMainBinding binding;

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
    }

    private void initViews() {

    }
}