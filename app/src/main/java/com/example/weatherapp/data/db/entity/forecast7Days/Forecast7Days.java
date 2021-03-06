package com.example.weatherapp.data.db.entity.forecast7Days;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.util.List;

@Entity(tableName = "forecast_7days")
public class Forecast7Days {

    public static final int FORECAST_7DAYS_ID = 1;

    @PrimaryKey(autoGenerate = false)
    public int id = 1;

    @TypeConverters(ForecastDailyConverter.class)
    public List<ForecastDaily> forecastDaily;

    public Forecast7Days(List<ForecastDaily> forecastDaily) {
        this.forecastDaily = forecastDaily;
    }

    public List<ForecastDaily> getForecastDaily() {
        return forecastDaily;
    }
}
