package com.example.weatherapp.ui.setting.search;

import androidx.lifecycle.Observer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.weatherapp.R;
import com.example.weatherapp.data.db.entity.currentWeather.CurrentWeather;
import com.example.weatherapp.data.db.entity.forecast7Days.Forecast7Days;
import com.example.weatherapp.data.network.WeatherApiRequest;
import com.example.weatherapp.data.network.response.CurrentWeatherResponse;
import com.example.weatherapp.data.network.response.Forecast7DaysResponse;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchWeatherFragment extends Fragment {

    private SearchWeatherViewModel mViewModel;

    //поля на лейауте
    private TextInputLayout cityInput;
    private Button btnSearch;

    // имя и координаты для запроса
    String cityName;
    float lat;
    float lon;

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


        //координаты из базы
        mViewModel.getCurrentWeather().observe(getViewLifecycleOwner() , new Observer<CurrentWeather>() {
            @Override
            public void onChanged(CurrentWeather currentWeather) {
                lat = currentWeather.coordCurrentWeather.getLat();
                lon = currentWeather.coordCurrentWeather.getLon();
                Log.d("CurrentWeatherCoord", String.valueOf(lat)+111);
            }
        });


        // TODO: Use the ViewModel
        //при нажатии на кнопку отправлять 2 запроса
        //на текущую погоду и на прогноз
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //считать название города из поля ввода
                cityName = cityInput.getEditText().getText().toString();

                WeatherApiRequest jsonPlaceHolderApi = WeatherApiRequest.invoke();

                Call<CurrentWeatherResponse> callCurrentWeather = jsonPlaceHolderApi.getCurrentWeather(cityName, "ru", "metric");
                Call<Forecast7DaysResponse> callForecast =jsonPlaceHolderApi.get7DaysForecast(lat, lon, "hourly", "ru", "metric");

                //если есть подключение к интернету отправляем запросы
                if (WeatherApiRequest.isOnline(getContext())) {

                    //асинхронный запрос на текущую погоду
                    callCurrentWeather.enqueue(new Callback<CurrentWeatherResponse>() {
                        @Override
                        public void onResponse(Call<CurrentWeatherResponse> call, Response<CurrentWeatherResponse> response) {

                            CurrentWeatherResponse val = response.body();

                            //обновляем базу
                            mViewModel.upsert(new CurrentWeather(val.getCoord(), val.getWeather().get(0), val.getMain(),
                                    val.getWind(), val.getClouds(), val.getName()));
                        }

                        @Override
                        public void onFailure(Call<CurrentWeatherResponse> call, Throwable t) {

                            //TODO: сделать диалологовое окно с ошибкой
                            Log.e("InResponse", "exeption", t);
                        }
                    });

                    //асинхронный запрос на прогноз
                    callForecast.enqueue(new Callback<Forecast7DaysResponse>() {
                        @Override
                        public void onResponse(Call<Forecast7DaysResponse> call, Response<Forecast7DaysResponse> response) {
                            //обновляем в базу
                            Forecast7DaysResponse val = response.body();
                            mViewModel.upsert(new Forecast7Days(val.getDaily()));

                        }

                        @Override
                        public void onFailure(Call<Forecast7DaysResponse> call, Throwable t) {

                            //TODO: сделать диалологовое окно с ошибкой
                        }
                    });

                } else {

                    //TODO: сделать диалологовое окно с ошибкой

                    Log.d("Network", "no interent connection");
                    Toast.makeText(getContext(), "no Internet Connection", Toast.LENGTH_SHORT);
                }
            }
        });

    }

}