package com.example.weatherapp.ui.setting.search;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.weatherapp.R;
import com.example.weatherapp.data.db.entity.currentWeather.CurrentWeather;
import com.example.weatherapp.data.db.entity.forecast7Days.Forecast7Days;
import com.example.weatherapp.data.network.WeatherApiRequest;
import com.example.weatherapp.data.network.request.ApiCalls;
import com.example.weatherapp.data.network.response.CurrentWeatherResponse;
import com.example.weatherapp.data.network.response.Forecast7DaysResponse;
import com.example.weatherapp.ui.dialogs.MapDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchWeatherFragment extends Fragment {

    private SearchWeatherViewModel mViewModel;

    private TextInputLayout cityInput;
    private Button btnSearch;

    // имя и координаты для запроса
    String cityName;


    public static SearchWeatherFragment newInstance() {
        return new SearchWeatherFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_weather_fragment, container, false);
        //инициализация
        cityInput = view.findViewById(R.id.cityInput);
        btnSearch = view.findViewById(R.id.btnSearch);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SearchWeatherViewModel.class);
//        mViewModel = new ViewModelProvider(this).get(SearchWeatherViewModel.class);

        // TODO: Use the ViewModel
        //при нажатии на кнопку отправлять 2 запроса
        //на текущую погоду и на прогноз
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //считать название города из поля ввода
                cityName = cityInput.getEditText().getText().toString();
                if (cityName == null || cityName.isEmpty()) {
                    cityInput.setErrorEnabled(true);
                    cityInput.setError("Поле не может быть пустым");
                } else {
                    cityInput.setErrorEnabled(false);

                    ApiCalls apiCalls = new ApiCalls(getContext(), getView(), mViewModel);
                    apiCalls.getWeather(cityName);

                }
            }
        });

    }
}