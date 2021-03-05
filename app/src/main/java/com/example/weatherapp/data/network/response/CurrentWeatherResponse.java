package com.example.weatherapp.data.network.response;


import com.example.weatherapp.data.db.entity.CloudsCurrentWeather;
import com.example.weatherapp.data.db.entity.CoordCurrentWeather;
import com.example.weatherapp.data.db.entity.MainCurrentWeather;
import com.example.weatherapp.data.db.entity.WeatherCurrentWeather;
import com.example.weatherapp.data.db.entity.WindCurrentWeather;

import java.util.List;

public class CurrentWeatherResponse {

    CoordCurrentWeather coord;
    List<WeatherCurrentWeather> weather;
    MainCurrentWeather main;
    WindCurrentWeather wind;
    CloudsCurrentWeather clouds;

    int id;
    String name;
    int code;

    public CoordCurrentWeather getCoord() {
        return coord;
    }

    public List<WeatherCurrentWeather> getWeather() {
        return weather;
    }

    public MainCurrentWeather getMain() {
        return main;
    }

    public WindCurrentWeather getWind() {
        return wind;
    }

    public CloudsCurrentWeather getClouds() {
        return clouds;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }
}
