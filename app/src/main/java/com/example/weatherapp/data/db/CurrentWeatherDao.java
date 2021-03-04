package com.example.weatherapp.data.db;

import com.example.weatherapp.data.db.entity.CurrentWeather;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.weatherapp.data.db.entity.CurrentWeather;

@Dao
public interface CurrentWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void upsert(CurrentWeather currentWeather);

    @Query("select * from current_weather where id = CURRENT_WEATHER_ID")
    LiveData<CurrentWeather> getCurrentWeather();
}
