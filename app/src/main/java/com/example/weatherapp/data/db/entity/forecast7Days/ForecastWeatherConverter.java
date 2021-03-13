package com.example.weatherapp.data.db.entity.forecast7Days;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ForecastWeatherConverter {
    @TypeConverter
    public static List<WeatherForecast> fromString(String value) {
        Type listType = new TypeToken<List<ForecastDaily>>() {}.getType();
        List<WeatherForecast> notifications = new Gson().fromJson(value,listType);
        return notifications;
    }

    @TypeConverter
    public static String listToString(List<WeatherForecast> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }
}
