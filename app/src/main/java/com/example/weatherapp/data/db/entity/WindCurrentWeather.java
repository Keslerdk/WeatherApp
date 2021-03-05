package com.example.weatherapp.data.db.entity;


public class WindCurrentWeather {
    public int speed;
    public int deg;

    public WindCurrentWeather(int speed, int deg) {
        this.speed = speed;
        this.deg = deg;
    }

    public int getSpeed() {
        return speed;
    }

    public int getDeg() {
        return deg;
    }
}
