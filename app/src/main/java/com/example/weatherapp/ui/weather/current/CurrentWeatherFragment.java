package com.example.weatherapp.ui.weather.current;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.weatherapp.R;
import com.example.weatherapp.data.network.response.CurrentWeatherResponse;
import com.example.weatherapp.data.network.WeatherApiRequest;

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
        mViewModel = new ViewModelProvider(this).get(CurrentWeatherViewModel.class);
        // TODO: Use the ViewModel
        WeatherApiRequest jsonPlaceHolderApi = WeatherApiRequest.invoke();
        Call<CurrentWeatherResponse> call = jsonPlaceHolderApi.getCurrentWeather("London");
        if (WeatherApiRequest.isOnline(this.getContext())) {
            call.enqueue(new Callback<CurrentWeatherResponse>() {
                @Override
                public void onResponse(Call<CurrentWeatherResponse> call, Response<CurrentWeatherResponse> response) {
                }

                @Override
                public void onFailure(Call<CurrentWeatherResponse> call, Throwable t) {

                }
            });
        } else {
            Log.d("Network", "no interent connection");
            Toast.makeText(this.getContext(), "no Internet Connection", Toast.LENGTH_SHORT);
        }
    }


}