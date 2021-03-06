package com.example.weatherapp.data.db.entity.forecast7Days;

import androidx.room.Embedded;

import java.util.List;

public class ForecastDaily {
    @Embedded(prefix = "tempForecast")
    public TempForecast tempForecast;
    @Embedded(prefix = "feelLikeForecast")
    public FeelsLikeForecast feelsLikeForecast;
    public float humidity;
    public float wind_speed;
    public float wind_deg;
    @Embedded(prefix = "weatherForecast")
    public WeatherForecast weatherForecast;
    public float clouds;

    public ForecastDaily(TempForecast tempForecast, FeelsLikeForecast feelsLikeForecast,
                         float humidity, float wind_speed, float wind_deg,
                         WeatherForecast weatherForecast, float clouds) {
        this.tempForecast = tempForecast;
        this.feelsLikeForecast = feelsLikeForecast;
        this.humidity = humidity;
        this.wind_speed = wind_speed;
        this.wind_deg = wind_deg;
        this.weatherForecast = weatherForecast;
        this.clouds = clouds;
    }

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

    public WeatherForecast getWeatherForecast() {
        return weatherForecast;
    }

    public float getClouds() {
        return clouds;
    }
}
