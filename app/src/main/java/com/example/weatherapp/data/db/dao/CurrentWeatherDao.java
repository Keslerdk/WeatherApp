package com.example.weatherapp.data.db.dao;

import com.example.weatherapp.data.db.entity.currentWeather.CurrentWeather;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface CurrentWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void upsert(CurrentWeather currentWeather);

    @Query("select * from current_weather where id = 0")
    LiveData<CurrentWeather> getCurrentWeather();
}
