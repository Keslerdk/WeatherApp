package com.example.weatherapp.data.db.entity.currentWeather;


public class WindCurrentWeather {
    public float speed;
    public float deg;

    public WindCurrentWeather(float speed, float deg) {
        this.speed = speed;
        this.deg = deg;
    }

    public float getSpeed() {
        return speed;
    }

    public float getDeg() {
        return deg;
    }
}
