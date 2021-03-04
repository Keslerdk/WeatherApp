package com.example.weatherapp.data.db;


import android.content.Context;

import androidx.annotation.AnyRes;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.weatherapp.data.db.entity.CurrentWeather;

import kotlin.jvm.Volatile;

@Database( entities = {CurrentWeather.class},
    version = 1
)
public abstract class ForecastDatabase extends RoomDatabase {

    public abstract CurrentWeatherDao currentWeatherDao();

    @Volatile public static ForecastDatabase instance = null;

    private static synchronized ForecastDatabase invoke(Context context) {
        if (instance==null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), ForecastDatabase.class,
                    "forecast.db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}
