package com.pandacorp.weatherui.screens;

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
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import com.pandacorp.weatherui.R;
import com.pandacorp.weatherui.databinding.ScreenMainBinding;

public class MainScreen extends Fragment {
    private ScreenMainBinding binding;
    private NavController navController;

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