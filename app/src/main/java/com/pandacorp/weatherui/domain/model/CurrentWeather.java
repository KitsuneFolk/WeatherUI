package com.pandacorp.weatherui.domain.model;


import java.io.Serializable;
import java.util.List;

public class CurrentWeather implements Serializable {
    private List<Weather> weather;
    private Main main;

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }
}