package com.pandacorp.weatherui;

import static org.junit.Assert.assertEquals;

import androidx.core.util.Pair;

import com.pandacorp.weatherui.presentation.utils.HaversineCalculator;

import org.junit.Test;

public class HaversineDistanceTest {
    private static final double DELTA = 0.050; // Delta in KM

    @Test
    public void HaversineDistance_SameLocation() {
        Pair<Double, Double> location = new Pair<>(40.0, -75.0);
        double distance = HaversineCalculator.haversineDistance(location, location);
        assertEquals(0.0, distance, DELTA);
    }
    @Test
    public void HaversineDistancePrecise_ExpectedDistance11meters_True() {
        Pair<Double, Double> location1 = Pair.create(50.4501, -30.5234);
        Pair<Double, Double> location2 = Pair.create(50.4502, -30.5234);

        double expectedDistance = 0.01112;

        double result = HaversineCalculator.haversineDistance(location1, location2);

        assertEquals(expectedDistance, result, DELTA);
    }

    @Test
    public void HaversineDistance_MoreThan3000km_True() {
        Pair<Double, Double> location1 = Pair.create(40.7128, -74.0060); // New York City
        Pair<Double, Double> location2 = Pair.create(34.0522, -118.2437); // Los Angeles

        double result = HaversineCalculator.haversineDistance(location1, location2);

        assert(result > 3000);
    }

}