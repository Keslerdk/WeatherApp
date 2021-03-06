package com.example.weatherapp.data.db.entity;

public class CoordCurrentWeather {
    float lon;
    float lat;

    public CoordCurrentWeather(float lon, float lat) {
        this.lon = lon;
        this.lat = lat;
    }

    public float getLon() {
        return lon;
    }

    public float getLat() {
        return lat;
    }
}
