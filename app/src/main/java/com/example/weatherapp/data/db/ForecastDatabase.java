package com.example.weatherapp.data.db;


import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.AnyRes;
import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.weatherapp.data.db.entity.CloudsCurrentWeather;
import com.example.weatherapp.data.db.entity.CoordCurrentWeather;
import com.example.weatherapp.data.db.entity.CurrentWeather;
import com.example.weatherapp.data.db.entity.MainCurrentWeather;
import com.example.weatherapp.data.db.entity.WeatherCurrentWeather;
import com.example.weatherapp.data.db.entity.WindCurrentWeather;

import kotlin.jvm.Volatile;

@Database(entities = {CurrentWeather.class},
        version = 4
)
public abstract class ForecastDatabase extends RoomDatabase {

    public abstract CurrentWeatherDao currentWeatherDao();

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

        public PopulatedbAsyncTask(ForecastDatabase db) {
            currentWeatherDao = db.currentWeatherDao();
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
