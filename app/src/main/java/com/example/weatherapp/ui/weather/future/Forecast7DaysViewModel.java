package com.example.weatherapp.ui.weather.future;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.weatherapp.data.db.ForecastRepo;
import com.example.weatherapp.data.db.entity.forecast7Days.Forecast7Days;

public class Forecast7DaysViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel
    public ForecastRepo forecastRepo;
    public LiveData<Forecast7Days> forecast7DaysLiveData;


    public Forecast7DaysViewModel(@NonNull Application application) {
        super(application);
        forecastRepo = new ForecastRepo(application);
        forecast7DaysLiveData = forecastRepo.getForecast7Days();
    }

    public LiveData<Forecast7Days> getForecast7Days() {
        return forecast7DaysLiveData;
    }
}