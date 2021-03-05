package com.example.weatherapp.data.db.entity;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "current_weather")
public class CurrentWeather {
    public int CURRENT_WEATHER_ID = 0;

    @Embedded(prefix = "weather")
    public WeatherCurrentWeather weather;
    @Embedded(prefix = "main")
    public MainCurrentWeather main;
    @Embedded(prefix = "wind")
    public WindCurrentWeather wind;
    @Embedded(prefix = "clouds")
    public CloudsCurrentWeather clouds;

    public String name;

    @PrimaryKey(autoGenerate = false)
    public int id = CURRENT_WEATHER_ID;

    public CurrentWeather(WeatherCurrentWeather weather, MainCurrentWeather main, WindCurrentWeather wind, CloudsCurrentWeather clouds, String name) {
        this.weather = weather;
        this.main = main;
        this.wind = wind;
        this.clouds = clouds;
        this.name = name;
    }

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
