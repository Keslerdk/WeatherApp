package com.example.weatherapp.data.db.entity.currentWeather;

public class MainCurrentWeather {
    public float temp;
    public float feels_like;
    public float humidity;

    public MainCurrentWeather(float temp, float feels_like, float humidity) {
        this.temp = temp;
        this.feels_like = feels_like;
        this.humidity = humidity;
    }

    public float getTemp() {
        return temp;
    }

    public float getFeels_like() {
        return feels_like;
    }

    public float getHumidity() {
        return humidity;
    }
}
