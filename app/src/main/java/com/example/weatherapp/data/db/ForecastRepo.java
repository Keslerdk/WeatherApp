package com.example.weatherapp.data.db;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.weatherapp.data.db.dao.CurrentWeatherDao;
import com.example.weatherapp.data.db.dao.FavouritesDao;
import com.example.weatherapp.data.db.dao.Forecast7DaysDao;
import com.example.weatherapp.data.db.entity.currentWeather.CurrentWeather;
import com.example.weatherapp.data.db.entity.favourites.Favourites;
import com.example.weatherapp.data.db.entity.forecast7Days.Forecast7Days;

import java.util.List;

public class ForecastRepo {
    private CurrentWeatherDao currentWeatherDao;
    private LiveData<CurrentWeather> weatherLiveData;

    private Forecast7DaysDao forecast7DaysDao;
    private  LiveData<Forecast7Days>  forecast7DaysLiveData;

    private FavouritesDao favouritesDao;
    private LiveData<List<Favourites>> favouritesLiveData;
    private Favourites itemFav;

    public ForecastRepo(Application application) {
        ForecastDatabase forecastDatabase = ForecastDatabase.getDatabase(application);
        currentWeatherDao = forecastDatabase.currentWeatherDao();
        weatherLiveData = currentWeatherDao.getCurrentWeather();

        forecast7DaysDao = forecastDatabase.forecast7DaysDao();
        forecast7DaysLiveData = forecast7DaysDao.getForecast7Days();

        favouritesDao = forecastDatabase.favouritesDao();
        favouritesLiveData = favouritesDao.getFavourites();

    }

    //current weather
    public void upsert(CurrentWeather currentWeather) {
        new UpsertAsyncTask(currentWeatherDao).execute(currentWeather);
    }
    public void updateIsFavourite(boolean isfavourite) {
        new UpdateIsFavouriteAsyncTask(currentWeatherDao).execute(isfavourite);
    }

    //forecast 7 days
    public void upsert(Forecast7Days forecast7Days) {
        new UpsertAsyncTask7Days(forecast7DaysDao).execute(forecast7Days);
    }

    //favourites
    public void insert(Favourites favourites) {
        new InsertAsyncTaskRavourites(favouritesDao).execute(favourites);
    }

    public void update(Favourites favourites) {
        new UpdateAsyncTaskRavourites(favouritesDao).execute(favourites);
    }

    public void delete(Favourites favourites) {
         new DeleteAsyncTaskRavourites(favouritesDao).execute(favourites);
    }


    public LiveData<CurrentWeather> getCurrentWeather() {
        return weatherLiveData;
    }

    public LiveData<Forecast7Days> getForecast7Days() {return  forecast7DaysLiveData;}

    public LiveData<List<Favourites>> getFavourites() {
        return favouritesLiveData;
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

    private class InsertAsyncTaskRavourites extends AsyncTask<Favourites, Void, Void>{

        public FavouritesDao favouritesDao;

        public InsertAsyncTaskRavourites(FavouritesDao favouritesDao) {
            this.favouritesDao= favouritesDao;
        }

        @Override
        protected Void doInBackground(Favourites... favourites) {
            favouritesDao.insert(favourites[0]);
            return null;
        }
    }

    private class UpdateAsyncTaskRavourites extends AsyncTask<Favourites, Void, Void>{
        public FavouritesDao favouritesDao;
        public UpdateAsyncTaskRavourites(FavouritesDao favouritesDao) {
            this.favouritesDao = favouritesDao;
        }

        @Override
        protected Void doInBackground(Favourites... favourites) {
            favouritesDao.update(favourites[0]);
            return null;
        }
    }

    private class DeleteAsyncTaskRavourites extends AsyncTask<Favourites, Void, Void>{

        public FavouritesDao favouritesDao;
        public DeleteAsyncTaskRavourites(FavouritesDao favouritesDao) {
            this.favouritesDao = favouritesDao;
        }

        @Override
        protected Void doInBackground(Favourites... favourites) {
            favouritesDao.delete(favourites[0]);
            return null;
        }
    }

    private class UpdateIsFavouriteAsyncTask extends  AsyncTask<Boolean, Void, Void> {
        public CurrentWeatherDao currentWeatherDao;

        public UpdateIsFavouriteAsyncTask(CurrentWeatherDao currentWeatherDao) {
            this.currentWeatherDao = currentWeatherDao;
        }

        @Override
        protected Void doInBackground(Boolean... booleans) {
            currentWeatherDao.updateIsFavourite(booleans[0]);
            return null;
        }
    }
}
