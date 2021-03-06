package com.example.weatherapp.ui.weather.current;

import androidx.lifecycle.Observer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.weatherapp.R;
import com.example.weatherapp.data.db.entity.CurrentWeather;

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

        //просмотр текущего города в базе
        mViewModel.getCurrentWeather().observe(getViewLifecycleOwner() , new Observer<CurrentWeather>() {
            @Override
            public void onChanged(CurrentWeather currentWeather) {
                Toast.makeText(getContext(), currentWeather.name, Toast.LENGTH_SHORT).show();
            }
        });

    }

}