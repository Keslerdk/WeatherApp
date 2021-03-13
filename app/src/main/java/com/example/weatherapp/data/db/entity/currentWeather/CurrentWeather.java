package com.example.weatherapp.data.db.entity.currentWeather;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "current_weather")
public class CurrentWeather {
    public int CURRENT_WEATHER_ID = 0;

    @Embedded(prefix = "coord")
    public  CoordCurrentWeather coordCurrentWeather;
    @Embedded(prefix = "weather")
    public WeatherCurrentWeather weather;
    @Embedded(prefix = "main")
    public MainCurrentWeather main;
    @Embedded(prefix = "wind")
    public WindCurrentWeather wind;
    @Embedded(prefix = "clouds")
    public CloudsCurrentWeather clouds;

    public int dt;
    public String name;
    @SerializedName("id")
    public int idCity;
    public boolean isFavourite;

    @PrimaryKey(autoGenerate = false)
    public int id = CURRENT_WEATHER_ID;

    public CurrentWeather(CoordCurrentWeather coordCurrentWeather, WeatherCurrentWeather weather,
                          MainCurrentWeather main, WindCurrentWeather wind, CloudsCurrentWeather clouds,int dt, String name, int idCity) {
        this.coordCurrentWeather = coordCurrentWeather;
        this.weather = weather;
        this.main = main;
        this.wind = wind;
        this.clouds = clouds;
        this.name = name;
        this.idCity = idCity;
        this.isFavourite = false;
        this.dt=dt;
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

    public int getIdCity() {
        return idCity;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    public int getDt() {
        return dt;
    }
}
