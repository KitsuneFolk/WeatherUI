package com.pandacorp.weatherui.presentation.utils;

import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.content.ContextCompat;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.Task;

public class LocationHelper {
    private static final String[] permissions = {
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
    };

    public static void canGetLocation(Context context, final LocationCallback callback) {
        if (!hasPermissions(context)) {
            // Doesn't have permission
            callback.onResult(false);
        } else {
            checkLocationSettings(context, callback::onResult);
        }
    }

    private static boolean hasPermissions(Context context) {
        for (String permission : LocationHelper.permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private static void checkLocationSettings(Context context, final LocationSettingsCallback callback) {
        LocationRequest locationRequest = getDefaultRequest();

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
                .setAlwaysShow(true);

        Task<LocationSettingsResponse> task = LocationServices.getSettingsClient(context)
                .checkLocationSettings(builder.build());

        task.addOnSuccessListener(locationSettingsResponse -> callback.onResult(true));

        task.addOnFailureListener(e -> callback.onResult(false));
    }

    private static LocationRequest getDefaultRequest() {
        return new LocationRequest.Builder(1000L)
                .setMinUpdateIntervalMillis(1000L)
                .setMaxUpdateDelayMillis(1000L)
                .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
                .build();
    }

    public interface LocationCallback {
        void onResult(boolean isSatisfied);
    }

    public interface LocationSettingsCallback {
        void onResult(boolean isSatisfied);
    }
}