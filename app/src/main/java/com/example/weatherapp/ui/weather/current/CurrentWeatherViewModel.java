package com.example.weatherapp.ui.weather.current;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.weatherapp.data.db.ForecastRepo;
import com.example.weatherapp.data.db.entity.currentWeather.CurrentWeather;
import com.example.weatherapp.data.db.entity.favourites.Favourites;
;import java.util.List;

public class CurrentWeatherViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel
    public ForecastRepo forecastRepo;

    public LiveData<CurrentWeather> currentWeatherLiveData;

    public LiveData<List<Favourites>> favouritesLiveData;

    public CurrentWeatherViewModel (@NonNull Application application) {
        super(application);
        forecastRepo = new ForecastRepo(application);
        currentWeatherLiveData = forecastRepo.getCurrentWeather();
        favouritesLiveData = forecastRepo.getFavourites();
    }
    public void upsert(CurrentWeather currentWeather) {
        forecastRepo.upsert(currentWeather);
    }
    public void updateIsFavourite(boolean isfavourite) {forecastRepo.updateIsFavourite(isfavourite);}

    public void insert(Favourites favourites) {
        forecastRepo.insert(favourites);
    }
    public void delete(Favourites favourites) {
        forecastRepo.delete(favourites);
    }

    public LiveData<CurrentWeather> getCurrentWeather() {
        return currentWeatherLiveData;
    }

    public LiveData<List<Favourites>> getFavourites() {return favouritesLiveData;}

    public LiveData<Favourites> getItemFav(int cityId) {return forecastRepo.getItemFav(cityId);}
}