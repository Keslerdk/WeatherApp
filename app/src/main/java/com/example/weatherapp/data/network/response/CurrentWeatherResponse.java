package com.example.weatherapp.data.network.response;


import com.example.weatherapp.data.db.entity.currentWeather.CloudsCurrentWeather;
import com.example.weatherapp.data.db.entity.currentWeather.CoordCurrentWeather;
import com.example.weatherapp.data.db.entity.currentWeather.MainCurrentWeather;
import com.example.weatherapp.data.db.entity.currentWeather.WeatherCurrentWeather;
import com.example.weatherapp.data.db.entity.currentWeather.WindCurrentWeather;

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
    int dt;

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

    public int getDt() {
        return dt;
    }
}
