package com.pandacorp.weatherui.adapter;

import android.graphics.drawable.Drawable;

public class SettingsItem {
    private final String value;
    private final String title;
    private final Drawable drawable;

    public SettingsItem(String value, String title, Drawable drawable) {
        this.value = value;
        this.title = title;
        this.drawable = drawable;
    }

    public String getValue() {
        return value;
    }

    public String getTitle() {
        return title;
    }

    public Drawable getDrawable() {
        return drawable;
    }
}