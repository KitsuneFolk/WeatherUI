package com.pandacorp.weatherui.presentation.utils;

import androidx.core.util.Pair;

public class HaversineCalculator {
    /**
     * Calculates the distance in km between 2 locations
     *
     * @param location1 a double pair of Latitude and Longitude
     * @param location2 a double pair of Latitude and Longitude
     * @return a distance in meters between 2 pairs of Latitude and Longitude
     */
    public static double haversineDistance(Pair<Double, Double> location1, Pair<Double, Double> location2) {
        double earthRadius = 6378; // Radius of the Earth in km

        double lat1Rad = Math.toRadians(location1.first);
        double lon1Rad = Math.toRadians(location1.second);
        double lat2Rad = Math.toRadians(location2.first);
        double lon2Rad = Math.toRadians(location2.second);

        double dLat = lat2Rad - lat1Rad;
        double dLon = lon2Rad - lon1Rad;

        double a = Math.pow(Math.sin(dLat / 2), 2) + Math.cos(lat1Rad) * Math.cos(lat2Rad) * Math.pow(Math.sin(dLon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return earthRadius * c;
    }
}