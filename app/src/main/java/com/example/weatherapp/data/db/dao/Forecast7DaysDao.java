package com.example.weatherapp.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.weatherapp.data.db.entity.currentWeather.CurrentWeather;
import com.example.weatherapp.data.db.entity.forecast7Days.Forecast7Days;

@Dao
public interface Forecast7DaysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void upsert(Forecast7Days forecast7Days);

    @Query("select * from forecast_7days where id = 1")
    LiveData<Forecast7Days> getForecast7Days();
}
