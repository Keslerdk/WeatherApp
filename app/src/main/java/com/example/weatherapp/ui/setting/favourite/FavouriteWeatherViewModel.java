package com.example.weatherapp.ui.setting.favourite;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.weatherapp.R;
import com.example.weatherapp.data.db.ForecastRepo;
import com.example.weatherapp.data.db.entity.favourites.Favourites;

import java.util.List;

public class FavouriteWeatherViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel
    public ForecastRepo forecastRepo;

    public LiveData<List<Favourites>> favouritesLiveData;

    public FavouriteWeatherViewModel(@NonNull Application application) {
        super(application);
        forecastRepo = new ForecastRepo(application);
        favouritesLiveData = forecastRepo.getFavourites();
    }

    //favourites
    public void insert(Favourites favourites) {
        forecastRepo.insert(favourites);
    }
    public void delete(Favourites favourites) {
        forecastRepo.delete(favourites);
    }
    //current weather
    public void updateIsFavourite(boolean isfavourite) {forecastRepo.updateIsFavourite(isfavourite);}

    public LiveData<List<Favourites>> getFavourites() {
        return  favouritesLiveData;
    }
}