package com.pandacorp.weatherui.presentation.utils;

import android.content.Context;
import android.content.res.Configuration;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.LocaleListCompat;
import androidx.preference.PreferenceManager;

import com.pandacorp.weatherui.R;

import java.util.Locale;

public class PreferenceHandler {
    private static final String themeFollowSystem = "follow_system";
    private static final String themeBlue = "blue";
    private static final String themeDark = "dark";
    private static final String themeRed = "red";
    private static final String themePurple = "purple";

    public static void setTheme(Context context) {
        setTheme(context, PreferenceManager.getDefaultSharedPreferences(context)
                .getString(Constants.Dialog.THEME, context.getString(R.string.defaultTheme)));
    }

    public static void setTheme(Context context, String theme) {
        switch (theme) {
            case themeFollowSystem -> {
                if (isDeviceDarkMode(context)) {
                    context.setTheme(R.style.DarkTheme);
                } else {
                    context.setTheme(R.style.BlueTheme);
                }
            }
            case themeBlue -> context.setTheme(R.style.BlueTheme);
            case themeDark -> context.setTheme(R.style.DarkTheme);
            case themeRed -> context.setTheme(R.style.RedTheme);
            case themePurple -> context.setTheme(R.style.PurpleTheme);
        }
    }

    public static void setLanguage(Context context) {
        setLanguage(PreferenceManager.getDefaultSharedPreferences(context)
                .getString(Constants.Dialog.LANGUAGE, context.getResources().getString(R.string.defaultLanguage)));
    }

    public static void setLanguage(String language) {
        Locale.setDefault(new Locale(language)); // Still need to rewrite еру the default Locale despite using AppCompatDelegate
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(language));
    }

    private static boolean isDeviceDarkMode(Context context) {
        int nightModeFlags = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        return nightModeFlags == Configuration.UI_MODE_NIGHT_YES;
    }
}
