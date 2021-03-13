package com.example.weatherapp.data.db.entity.forecast7Days;

import androidx.room.Embedded;
import androidx.room.TypeConverters;

import java.util.List;

public class ForecastDaily {
    int dt;
    @Embedded(prefix = "tempForecast")
    public TempForecast temp;
    @Embedded(prefix = "feelLikeForecast")
    public FeelsLikeForecast feels_like;
    public float humidity;
    public float wind_speed;
    public float wind_deg;
    @Embedded(prefix = "weatherForecast")
    @TypeConverters(ForecastWeatherConverter.class)
    public List<WeatherForecast> weather;
    public float clouds;

    public ForecastDaily(int dt,TempForecast tempForecast, FeelsLikeForecast feelsLikeForecast,
                         float humidity, float wind_speed, float wind_deg,
                         List<WeatherForecast> weatherForecast, float clouds) {
        this.dt=dt;
        this.temp = tempForecast;
        this.feels_like = feelsLikeForecast;
        this.humidity = humidity;
        this.wind_speed = wind_speed;
        this.wind_deg = wind_deg;
        this.weather = weatherForecast;
        this.clouds = clouds;
    }

    public TempForecast getTempForecast() {
        return temp;
    }

    public FeelsLikeForecast getFeelsLikeForecast() {
        return feels_like;
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

    public List<WeatherForecast> getWeather() {
        return weather;
    }

    public float getClouds() {
        return clouds;
    }

    public int getDt() {
        return dt;
    }
}
