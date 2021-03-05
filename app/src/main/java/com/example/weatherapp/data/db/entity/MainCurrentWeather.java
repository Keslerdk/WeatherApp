package com.example.weatherapp.data.db.entity;

public class MainCurrentWeather {
    public int temp;
    public int feels_like;
    public int humidity;

    public MainCurrentWeather(int temp, int feels_like, int humidity) {
        this.temp = temp;
        this.feels_like = feels_like;
        this.humidity = humidity;
    }

    public int getTemp() {
        return temp;
    }

    public int getFeels_like() {
        return feels_like;
    }

    public int getHumidity() {
        return humidity;
    }
}
