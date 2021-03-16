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
    float lat = 2;
    float lon = 2;


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

                    firstApiCall(cityName);
                    //задержка чтобы вопсользовать координатами из прошлого запроса
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            secondApiCall();
                        }
                    }, 5000);
                }
            }
        });

    }

    private void showSnackBar(String text) {
        Snackbar snack = Snackbar.make(getView(), text, Snackbar.LENGTH_LONG);
        View view = snack.getView();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
        params.gravity = Gravity.TOP;
        view.setLayoutParams(params);
        snack.show();
    }

    private void firstApiCall(String cityName) {
        //асинхронный запрос на текущую погоду
        WeatherApiRequest jsonPlaceHolderApi = WeatherApiRequest.invoke();
        if (WeatherApiRequest.isOnline(getContext())) {

            Call<CurrentWeatherResponse> callCurrentWeather = jsonPlaceHolderApi.getCurrentWeather(cityName, "ru", "metric");
            callCurrentWeather.enqueue(new Callback<CurrentWeatherResponse>() {
                @Override
                public void onResponse(Call<CurrentWeatherResponse> call, Response<CurrentWeatherResponse> response) {
                    CurrentWeatherResponse val = response.body();
                    switch (response.code()) {
                        case (200):
                            //обновляем базу
                            mViewModel.upsert(new CurrentWeather(val.getCoord(), val.getWeather().get(0), val.getMain(),
                                    val.getWind(), val.getClouds(), val.getDt(), val.getName(), val.getId()));
                            //координаты для следующего запроса
                            lat = val.getCoord().getLat();
                            lon = val.getCoord().getLon();
                            break;
                        case (404):
                            showSnackBar("Не найдено");
                            break;
                        default:
                            showSnackBar("Что-то пошло не так. Попробуйте снова.");
                            break;
                    }
                    Navigation.findNavController(getView()).navigate(R.id.currentWeatherFragment);
                }

                @Override
                public void onFailure(Call<CurrentWeatherResponse> call, Throwable t) {
                    showSnackBar("Что-то пошло не так. Попробуйте снова");
                    Log.e("InResponse", "exeption", t);
                }
            });

        } else showSnackBar("Нет интернет соединения");
    }

    private void secondApiCall() {
        WeatherApiRequest jsonPlaceHolderApi = WeatherApiRequest.invoke();
        if (WeatherApiRequest.isOnline(getContext())) {
            if (lat != 2 || lon != 2) {
                // асинхронный запрос на прогноз
                Call<Forecast7DaysResponse> callForecast = jsonPlaceHolderApi.get7DaysForecast(lat, lon, "hourly", "ru", "metric");
                callForecast.enqueue(new Callback<Forecast7DaysResponse>() {
                    @Override
                    public void onResponse(Call<Forecast7DaysResponse> call, Response<Forecast7DaysResponse> response) {
                        //обновляем в базу
                        Forecast7DaysResponse val = response.body();
                        if (response.code() == 200)
                            mViewModel.upsert(new Forecast7Days(val.getDaily()));
                    }

                    @Override
                    public void onFailure(Call<Forecast7DaysResponse> call, Throwable t) {
                        showSnackBar("Что-то пошло не так. Попробуйте снова");
                    }
                });
            }

        } else showSnackBar("Нет интернет соединения");
    }
}