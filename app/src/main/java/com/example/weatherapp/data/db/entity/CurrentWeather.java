package com.example.weatherapp.data.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "current_weather")
public class CurrentWeather {
    int CURRENT_WEATHER_ID = 0;

    WeatherCurrentWeather weather;
    MainCurrentWeather main;
    WindCurrentWeather wind;
    CloudsCurrentWeather clouds;
    String name;

    @PrimaryKey(autoGenerate = false)
    int id = CURRENT_WEATHER_ID;


    public WeatherCurrentWeather getWeather() {
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

    public String getName() {
        return name;
    }
}
