package com.pandacorp.weatherui.presentation.utils;

public class TextFormat {
    public static String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        char firstChar = Character.toUpperCase(input.charAt(0));
        return firstChar + input.substring(1);
    }
}
