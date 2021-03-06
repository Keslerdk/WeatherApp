package com.example.weatherapp.data.db;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.weatherapp.data.db.dao.CurrentWeatherDao;
import com.example.weatherapp.data.db.dao.Forecast7DaysDao;
import com.example.weatherapp.data.db.entity.currentWeather.CurrentWeather;
import com.example.weatherapp.data.db.entity.forecast7Days.Forecast7Days;

public class ForecastRepo {
    private CurrentWeatherDao currentWeatherDao;
    private LiveData<CurrentWeather> weatherLiveData;

    private Forecast7DaysDao forecast7DaysDao;
    private  LiveData<Forecast7Days>  forecast7DaysLiveData;

    public ForecastRepo(Application application) {
        ForecastDatabase forecastDatabase = ForecastDatabase.getDatabase(application);
        currentWeatherDao = forecastDatabase.currentWeatherDao();
        weatherLiveData = currentWeatherDao.getCurrentWeather();

        forecast7DaysDao = forecastDatabase.forecast7DaysDao();
        forecast7DaysLiveData = forecast7DaysDao.getForecast7Days();
    }

    public void upsert(CurrentWeather currentWeather) {
        new UpsertAsyncTask(currentWeatherDao).execute(currentWeather);
    }

    public void upsert(Forecast7Days forecast7Days) {
        new UpsertAsyncTask7Days(forecast7DaysDao).execute(forecast7Days);
    }

    public LiveData<CurrentWeather> getCurrentWeather() {
        return weatherLiveData;
    }

    public LiveData<Forecast7Days> getForecast7Days() {return  forecast7DaysLiveData;}

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

    private class UpsertAsyncTask7Days extends AsyncTask<Forecast7Days, Void, Void>{

        private Forecast7DaysDao forecast7DaysDao;

        public UpsertAsyncTask7Days(Forecast7DaysDao forecast7DaysDao) {
            this.forecast7DaysDao = forecast7DaysDao;
        }

        @Override
        protected Void doInBackground(Forecast7Days... forecast7Days) {
            forecast7DaysDao.upsert(forecast7Days[0]);
            return null;
        }
    }
}
