package com.example.weatherapp.data.db.entity.forecast7Days;

public class TempForecast {
    public float day;
    public float night;

    public TempForecast(float day, float night) {
        this.day = day;
        this.night = night;
    }

    public float getDay() {
        return day;
    }

    public float getNight() {
        return night;
    }
}
