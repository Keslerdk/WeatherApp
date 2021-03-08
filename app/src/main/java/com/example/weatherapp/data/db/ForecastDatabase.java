package com.example.weatherapp.data.db;


import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.weatherapp.data.db.dao.CurrentWeatherDao;
import com.example.weatherapp.data.db.dao.FavouritesDao;
import com.example.weatherapp.data.db.dao.Forecast7DaysDao;
import com.example.weatherapp.data.db.entity.currentWeather.CloudsCurrentWeather;
import com.example.weatherapp.data.db.entity.currentWeather.CoordCurrentWeather;
import com.example.weatherapp.data.db.entity.currentWeather.CurrentWeather;
import com.example.weatherapp.data.db.entity.currentWeather.MainCurrentWeather;
import com.example.weatherapp.data.db.entity.currentWeather.WeatherCurrentWeather;
import com.example.weatherapp.data.db.entity.currentWeather.WindCurrentWeather;
import com.example.weatherapp.data.db.entity.favourites.Favourites;
import com.example.weatherapp.data.db.entity.forecast7Days.Forecast7Days;
import com.example.weatherapp.data.db.entity.forecast7Days.ForecastDaily;
import com.example.weatherapp.data.db.entity.forecast7Days.ForecastDailyConverter;

@Database(entities = {CurrentWeather.class, Forecast7Days.class, Favourites.class},
        version = 7
)
@TypeConverters(ForecastDailyConverter.class)
public abstract class ForecastDatabase extends RoomDatabase {

    public abstract CurrentWeatherDao currentWeatherDao();
    public abstract Forecast7DaysDao forecast7DaysDao();
    public abstract FavouritesDao favouritesDao();

    public static ForecastDatabase instance;

    /*
    private static synchronized ForecastDatabase invoke(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), ForecastDatabase.class,
                    "forecast.db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }*/

    public static ForecastDatabase getDatabase(final Context context) {
        if (instance == null) {
            synchronized (ForecastDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            ForecastDatabase.class, "forecast.db")
                            // Wipes and rebuilds instead of migrating if no Migration object.
                            // Migration is not part of this codelab.
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return instance;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulatedbAsyncTask(instance).execute();
        }
    };

    private static class PopulatedbAsyncTask extends AsyncTask<Void, Void, Void> {

        private CurrentWeatherDao currentWeatherDao;
        private Forecast7DaysDao forecast7DaysDao;
        private FavouritesDao favouritesDao;

        public PopulatedbAsyncTask(ForecastDatabase db) {
            currentWeatherDao = db.currentWeatherDao();
            forecast7DaysDao = db.forecast7DaysDao();
            favouritesDao = db.favouritesDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            currentWeatherDao.upsert(new CurrentWeather(new CoordCurrentWeather(11212, 12314),new WeatherCurrentWeather(1, "sv", "VDSV", "zv"),
                    new MainCurrentWeather(1, 2, 3),
                    new WindCurrentWeather(1, 2),
                    new CloudsCurrentWeather(1), "aaa"));
            return null;
        }
    }
}
