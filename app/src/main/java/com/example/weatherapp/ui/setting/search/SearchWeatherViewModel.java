package com.example.weatherapp.ui.setting.search;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.weatherapp.data.db.ForecastRepo;
import com.example.weatherapp.data.db.entity.currentWeather.CurrentWeather;
import com.example.weatherapp.data.db.entity.favourites.Favourites;
import com.example.weatherapp.data.db.entity.forecast7Days.Forecast7Days;

public class SearchWeatherViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel
    public ForecastRepo forecastRepo;

    public LiveData<CurrentWeather> currentWeatherLiveData;
    public LiveData<Forecast7Days> forecast7DaysLiveData;

    public SearchWeatherViewModel(@NonNull Application application) {
        super(application);
        forecastRepo = new ForecastRepo(application);
        currentWeatherLiveData = forecastRepo.getCurrentWeather();
        forecast7DaysLiveData = forecastRepo.getForecast7Days();
    }


    public void upsert(CurrentWeather currentWeather) {
        forecastRepo.upsert(currentWeather);
    }

    public void upsert(Forecast7Days forecast7Days){
        forecastRepo.upsert(forecast7Days);
    }

    public LiveData<CurrentWeather> getCurrentWeather() {
        return currentWeatherLiveData;
    }

    public LiveData<Forecast7Days> getForecast7Days() {return forecast7DaysLiveData;}
}