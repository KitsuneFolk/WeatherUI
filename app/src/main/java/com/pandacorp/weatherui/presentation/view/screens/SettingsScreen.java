package com.pandacorp.weatherui.presentation.view.screens;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import com.pandacorp.weatherui.R;
import com.pandacorp.weatherui.databinding.ScreenSettingsBinding;
import com.pandacorp.weatherui.presentation.utils.Constants;
import com.pandacorp.weatherui.presentation.utils.dialogs.SettingsDialog;

public class SettingsScreen extends Fragment {
    private ScreenSettingsBinding binding;
    private SettingsDialog languageDialog;
    private SettingsDialog themeDialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = ScreenSettingsBinding.inflate(inflater, container, false);

        initViews();

        return binding.getRoot();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        String dialogKey = null;
        if (themeDialog.isShowing()) {
            dialogKey = Constants.Dialog.THEME;
        } else if (languageDialog.isShowing()) {
            dialogKey = Constants.Dialog.LANGUAGE;
        }
        outState.putString(Constants.Dialog.BUNDLE_KEY, dialogKey);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        String showedDialog = savedInstanceState != null ? savedInstanceState.getString(Constants.Dialog.BUNDLE_KEY) : null;
        if (Constants.Dialog.THEME.equals(showedDialog)) {
            themeDialog.show();
        } else if (Constants.Dialog.LANGUAGE.equals(showedDialog)) {
            languageDialog.show();
        }
    }

    @Override
    public void onDestroy() {
        if (themeDialog != null) {
            themeDialog.dismiss();
        }
        if (languageDialog != null) {
            languageDialog.dismiss();
        }
        super.onDestroy();
    }

    private void initViews() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(requireContext());
        themeDialog = new SettingsDialog(requireContext(), Constants.Dialog.THEME);
        themeDialog.setOnValueAppliedListener(value -> requireActivity().recreate());
        languageDialog = new SettingsDialog(requireContext(), Constants.Dialog.LANGUAGE);
        languageDialog.setOnValueAppliedListener(value -> requireActivity().recreate());

        binding.toolbarInclude.toolbar.setTitle(R.string.settings);
        binding.toolbarInclude.toolbar.setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material);
        binding.toolbarInclude.toolbar.setNavigationOnClickListener(v -> requireActivity().getOnBackPressedDispatcher().onBackPressed());

        binding.versionTextView.setText(getResources().getString(R.string.version, getVersionName()));

        binding.themeLayout.setOnClickListener(v -> {
            if (isDialogShown()) return;
            themeDialog.show();
        });

        binding.themeTextView.setText(getThemeFromKey(sp.getString(
                Constants.Dialog.THEME,
                getResources().getString(R.string.defaultTheme)
        )));

        binding.languageLayout.setOnClickListener(v -> {
            if (isDialogShown()) return;
            languageDialog.show();
        });

        binding.languageTextView.setText(getLanguageFromKey(sp.getString(
                Constants.Dialog.LANGUAGE,
                getResources().getString(R.string.defaultLanguage)
        )));
    }

    private String getVersionName() {
        String version = "";
        try {
            version = requireContext().getPackageManager()
                    .getPackageInfo(requireContext().getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    private String getThemeFromKey(String key) {
        String[] themes = getResources().getStringArray(R.array.ThemesTitles);
        String[] keys = getResources().getStringArray(R.array.ThemesValues);

        for (int i = 0; i < keys.length; i++) {
            if (keys[i].equals(key)) {
                return themes[i];
            }
        }

        return "";
    }

    private String getLanguageFromKey(String key) {
        String[] languages = getResources().getStringArray(R.array.LanguagesTitles);
        String[] keys = getResources().getStringArray(R.array.LanguagesValues);

        for (int i = 0; i < keys.length; i++) {
            if (keys[i].equals(key)) {
                return languages[i];
            }
        }

        return "";
    }

    private boolean isDialogShown() {
        return (languageDialog.isShowing() || themeDialog.isShowing());
    }
}