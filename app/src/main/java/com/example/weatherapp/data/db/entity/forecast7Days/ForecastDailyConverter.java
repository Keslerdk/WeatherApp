package com.example.weatherapp.data.db.entity.forecast7Days;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ForecastDailyConverter {

    @TypeConverter
    public static List<ForecastDaily> fromString(String value) {
        Type listType = new TypeToken<List<ForecastDaily>>() {}.getType();
        List<ForecastDaily> notifications = new Gson().fromJson(value,listType);
        return notifications;
    }

    @TypeConverter
    public static String listToString(List<ForecastDaily> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }
}
