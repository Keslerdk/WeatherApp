package com.example.weatherapp.ui.weather.current;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.weatherapp.data.db.ForecastRepo;
import com.example.weatherapp.data.db.entity.CurrentWeather;
;import java.util.Objects;

import static java.util.Objects.*;

public class CurrentWeatherViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel
    public ForecastRepo forecastRepo;

    public LiveData<CurrentWeather> currentWeatherLiveData;

    public CurrentWeatherViewModel (@NonNull Application application) {
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