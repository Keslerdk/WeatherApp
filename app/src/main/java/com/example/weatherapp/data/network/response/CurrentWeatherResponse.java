package com.example.weatherapp.data.network.response;

public class CurrentWeatherResponse {
    CoordCurrentWeather coord;
    WeatherCurrentWeather weather;
    MainCurrentWeather main;
    WindCurrentWeather wind;
    CloudsCurrentWeather clouds;

    int id;
    String name;
    int code;
}
