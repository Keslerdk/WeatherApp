package com.example.weatherapp.ui.weather.current;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.weatherapp.R;
import com.example.weatherapp.data.db.entity.CloudsCurrentWeather;
import com.example.weatherapp.data.db.entity.CurrentWeather;
import com.example.weatherapp.data.db.entity.MainCurrentWeather;
import com.example.weatherapp.data.db.entity.WeatherCurrentWeather;
import com.example.weatherapp.data.db.entity.WindCurrentWeather;
import com.example.weatherapp.data.network.response.CurrentWeatherResponse;
import com.example.weatherapp.data.network.WeatherApiRequest;
import com.example.weatherapp.ui.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrentWeatherFragment extends Fragment {

    public static CurrentWeatherFragment newInstance() {
        return new CurrentWeatherFragment();
    }

    private CurrentWeatherViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.current_weather_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(CurrentWeatherViewModel.class);
//        mViewModel = new ViewModelProvider(this).get(CurrentWeatherViewModel.class);
        // TODO: Use the ViewModel

        mViewModel.upsert(new CurrentWeather(new WeatherCurrentWeather(1, "sv", "VDSV", "zv"),
                new MainCurrentWeather(1, 2, 3),
                new WindCurrentWeather(1, 2),
                new CloudsCurrentWeather(1), "Check"));
        mViewModel.getCurrentWeather().observe(getViewLifecycleOwner() , new Observer<CurrentWeather>() {
            @Override
            public void onChanged(CurrentWeather currentWeather) {
                Toast.makeText(getContext(), currentWeather.name, Toast.LENGTH_SHORT).show();
            }
        });

        WeatherApiRequest jsonPlaceHolderApi = WeatherApiRequest.invoke();
        Call<CurrentWeatherResponse> call = jsonPlaceHolderApi.getCurrentWeather("London");
        if (WeatherApiRequest.isOnline(this.getContext())) {
            call.enqueue(new Callback<CurrentWeatherResponse>() {
                @Override
                public void onResponse(Call<CurrentWeatherResponse> call, Response<CurrentWeatherResponse> response) {
                    CurrentWeatherResponse val = response.body();
                    mViewModel.upsert(new CurrentWeather(val.getWeather().get(0), val.getMain(),
                            val.getWind(), val.getClouds(), val.getName()));
                    Log.d("InResponse", String.valueOf(val.getName()));
                }

                @Override
                public void onFailure(Call<CurrentWeatherResponse> call, Throwable t) {
                    Log.e("InResponse", "exeption", t);
                }
            });
        } else {
            Log.d("Network", "no interent connection");
            Toast.makeText(this.getContext(), "no Internet Connection", Toast.LENGTH_SHORT);
        }
    }


}