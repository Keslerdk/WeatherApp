package com.example.weatherapp.ui.setting.search;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.weatherapp.data.db.ForecastRepo;
import com.example.weatherapp.data.db.entity.currentWeather.CurrentWeather;

public class SearchWeatherViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel
    public ForecastRepo forecastRepo;

    public LiveData<CurrentWeather> currentWeatherLiveData;

    public SearchWeatherViewModel(@NonNull Application application) {
        super(application);
        forecastRepo = new ForecastRepo(application);
        currentWeatherLiveData = forecastRepo.getCurrentWeather();
    }


    public void upsert(CurrentWeather currentWeather) {
        forecastRepo.upsert(currentWeather);
    }

    public LiveData<CurrentWeather> getCurrentWeather() {
        return currentWeatherLiveData;
    }
}