package com.pandacorp.weatherui.presentation.utils.dialogs;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.pandacorp.weatherui.R;
import com.pandacorp.weatherui.presentation.adapter.SettingsAdapter;
import com.pandacorp.weatherui.presentation.adapter.SettingsItem;
import com.pandacorp.weatherui.databinding.DialogSettingsBinding;
import com.pandacorp.weatherui.presentation.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class SettingsDialog extends CustomDialog {
    private DialogSettingsBinding binding;
    private final String preferenceKey;
    private final Context context;

    public SettingsDialog(@NonNull Context context, String preferenceKey) {
        super(context);
        this.context = context;
        this.preferenceKey = preferenceKey;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DialogSettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViews();
    }

    private void initViews() {
        String defaultValue;
        List<SettingsItem> itemsList;
        int title;
        if (preferenceKey.equals(Constants.Dialog.THEME)) {
            defaultValue = context.getResources().getString(R.string.defaultTheme);
            title = R.string.theme;
            itemsList = fillThemesList();
        } else if (preferenceKey.equals(Constants.Dialog.LANGUAGE)) {
            defaultValue = context.getResources().getString(R.string.defaultLanguage);
            title = R.string.language;
            itemsList = fillLanguagesList();
        } else {
            throw new IllegalArgumentException("PreferenceKey = " + preferenceKey);
        }
        SettingsAdapter adapter = new SettingsAdapter(itemsList, preferenceKey);
        adapter.setOnClickListener(settingsItem -> {
            String value = settingsItem.getValue();
            if (sp.getString(preferenceKey, defaultValue).equals(value)) {
                return;
            }
            sp.edit().putString(preferenceKey, value).apply();
            onValueAppliedListener.onApplied(value);
            cancel();
        });
        binding.title.setText(context.getResources().getText(title));
        binding.cancel.setOnClickListener(view -> cancel());
        binding.recyclerView.setAdapter(adapter);
    }

    private List<SettingsItem> fillThemesList() {
        String[] titlesList = context.getResources().getStringArray(R.array.ThemesTitles);
        String[] keysList = context.getResources().getStringArray(R.array.ThemesValues);
        TypedArray itemsList = context.getResources().obtainTypedArray(R.array.ThemesDrawables);
        List<SettingsItem> themesList = new ArrayList<>();
        for (int i = 0; i < keysList.length; i++) {
            String key = keysList[i];
            String title = titlesList[i];
            Drawable drawable = itemsList.getDrawable(i);
            themesList.add(new SettingsItem(key, title, drawable));
        }
        itemsList.recycle();
        return themesList;
    }

    private List<SettingsItem> fillLanguagesList() {
        String[] titlesList = context.getResources().getStringArray(R.array.LanguagesTitles);
        String[] keysList = context.getResources().getStringArray(R.array.LanguagesValues);
        TypedArray drawablesList = context.getResources().obtainTypedArray(R.array.LanguagesDrawables);
        List<SettingsItem> languagesList = new ArrayList<>();
        for (int i = 0; i < keysList.length; i++) {
            String key = keysList[i];
            String title = titlesList[i];
            Drawable drawable = drawablesList.getDrawable(i);
            languagesList.add(new SettingsItem(key, title, drawable));
        }
        drawablesList.recycle();
        return languagesList;
    }
}