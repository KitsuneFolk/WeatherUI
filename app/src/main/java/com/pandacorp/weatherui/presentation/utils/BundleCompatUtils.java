package com.pandacorp.weatherui.presentation.utils;

import android.os.Build;
import android.os.Bundle;

import java.io.Serializable;

public class BundleCompatUtils {
    public static <T extends Serializable> T getSerializableCompat(Bundle bundle, String key, Class<T> clazz) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return bundle.getSerializable(key, clazz);
        } else {
            return (T) bundle.getSerializable(key);
        }
    }
}