package com.example.weatherapp.data.network.response;

import java.util.List;

public class ForecastDaily {
    TempForecast tempForecast;
    FeelsLikeForecast feelsLikeForecast;
    float humidity;
    float wind_speed;
    float wind_deg;
    List<WeatherForecast> weatherForecast;
    float clouds;

    public TempForecast getTempForecast() {
        return tempForecast;
    }

    public FeelsLikeForecast getFeelsLikeForecast() {
        return feelsLikeForecast;
    }

    public float getHumidity() {
        return humidity;
    }

    public float getWind_speed() {
        return wind_speed;
    }

    public float getWind_deg() {
        return wind_deg;
    }

    public List<WeatherForecast> getWeatherForecast() {
        return weatherForecast;
    }

    public float getClouds() {
        return clouds;
    }
}
