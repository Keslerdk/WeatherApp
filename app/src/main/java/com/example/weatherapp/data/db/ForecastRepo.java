package com.example.weatherapp.data.db;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.weatherapp.data.db.entity.CurrentWeather;

import java.util.List;

public class ForecastRepo {
    private CurrentWeatherDao currentWeatherDao;
    private LiveData<CurrentWeather> weatherLiveData;

    public ForecastRepo(Application application) {
        ForecastDatabase forecastDatabase = ForecastDatabase.getDatabase(application);
        currentWeatherDao = forecastDatabase.currentWeatherDao();
        weatherLiveData = currentWeatherDao.getCurrentWeather();
    }

    public void upsert(CurrentWeather currentWeather) {
        new UpsertAsyncTask(currentWeatherDao).execute(currentWeather);
    }

    public LiveData<CurrentWeather> getCurrentWeather() {
        return weatherLiveData;
    }

    private class UpsertAsyncTask extends AsyncTask<CurrentWeather, Void, Void> {

        private CurrentWeatherDao currentWeatherDao;

        public UpsertAsyncTask(CurrentWeatherDao currentWeatherDao) {
            this.currentWeatherDao = currentWeatherDao;
        }

        @Override
        protected Void doInBackground(CurrentWeather... currentWeathers) {
            currentWeatherDao.upsert(currentWeathers[0]);
            return null;
        }
    }
}
