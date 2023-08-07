package com.pandacorp.weatherui.screens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.pandacorp.weatherui.R;
import com.pandacorp.weatherui.databinding.ScreenSettingsBinding;

public class SettingsScreen extends Fragment {
    private ScreenSettingsBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = ScreenSettingsBinding.inflate(getLayoutInflater());

        initViews();

        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    private void initViews() {
        binding.toolbarInclude.toolbar.setTitle(R.string.settings);
        binding.toolbarInclude.toolbar.setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material);
        binding.toolbarInclude.toolbar.setNavigationOnClickListener(v -> requireActivity().onBackPressed());
    }
}