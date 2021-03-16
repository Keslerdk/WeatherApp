package com.example.weatherapp.data.network.request;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import androidx.navigation.Navigation;

import com.example.weatherapp.R;
import com.example.weatherapp.data.db.entity.currentWeather.CurrentWeather;
import com.example.weatherapp.data.db.entity.forecast7Days.Forecast7Days;
import com.example.weatherapp.data.network.WeatherApiRequest;
import com.example.weatherapp.data.network.response.CurrentWeatherResponse;
import com.example.weatherapp.data.network.response.Forecast7DaysResponse;
import com.example.weatherapp.ui.setting.favourite.FavouriteWeatherViewModel;
import com.example.weatherapp.ui.setting.search.SearchWeatherFragment;
import com.example.weatherapp.ui.setting.search.SearchWeatherViewModel;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiCalls {

    Context context;
    View view;
    SearchWeatherViewModel mViewModelSearch;
    FavouriteWeatherViewModel mViewModelFav;
//    float lat;
//    float lon;

    public ApiCalls(Context context, View view, SearchWeatherViewModel mViewModel) {
        this.context = context;
        this.view = view;
        this.mViewModelSearch = mViewModel;
    }

    public ApiCalls(Context context, View view, FavouriteWeatherViewModel mViewModelFav) {
        this.context = context;
        this.view = view;
        this.mViewModelFav = mViewModelFav;
    }

    public void getWeather(String cityName) {
        firstApiCall(cityName);
    }

    private void firstApiCall(String cityName) {
        //асинхронный запрос на текущую погоду
        WeatherApiRequest jsonPlaceHolderApi = WeatherApiRequest.invoke();
        if (WeatherApiRequest.isOnline(context)) {

            Call<CurrentWeatherResponse> callCurrentWeather = jsonPlaceHolderApi.getCurrentWeather(cityName, "ru", "metric");
            callCurrentWeather.enqueue(new Callback<CurrentWeatherResponse>() {
                @Override
                public void onResponse(Call<CurrentWeatherResponse> call, Response<CurrentWeatherResponse> response) {
                    CurrentWeatherResponse val = response.body();
                    switch (response.code()) {
                        case (200):
                            //обновляем базу
                            if (mViewModelFav!=null) upsertViewModelCurrentWeather(mViewModelFav, new CurrentWeather(val.getCoord(),
                                    val.getWeather().get(0), val.getMain(),
                                    val.getWind(), val.getClouds(), val.getDt(), val.getName(), val.getId()));

                            else upsertViewModelCurrentWeather(mViewModelSearch, new CurrentWeather(val.getCoord(),
                                    val.getWeather().get(0), val.getMain(),
                                    val.getWind(), val.getClouds(), val.getDt(), val.getName(), val.getId()));
//                            mViewModel.upsert(new CurrentWeather(val.getCoord(), val.getWeather().get(0), val.getMain(),
//                                    val.getWind(), val.getClouds(), val.getDt(), val.getName(), val.getId()));
                            //координаты для следующего запроса
                            float lat = val.getCoord().getLat();
                            float lon = val.getCoord().getLon();

                            secondApiCall(lat, lon);
                            break;
                        case (404):
                            showSnackBar("Не найдено");
                            break;
                        default:
                            showSnackBar("Что-то пошло не так. Попробуйте снова.");
                            break;
                    }
//                    Navigation.findNavController(view).navigate(R.id.currentWeatherFragment);
                }

                @Override
                public void onFailure(Call<CurrentWeatherResponse> call, Throwable t) {
                    showSnackBar("Что-то пошло не так. Попробуйте снова");
                    Log.e("InResponse", "exeption", t);
                }
            });

        } else showSnackBar("Нет интернет соединения");
    }

    private void secondApiCall(float lat, float lon) {
        WeatherApiRequest jsonPlaceHolderApi = WeatherApiRequest.invoke();
        if (WeatherApiRequest.isOnline(context)) {
            if (lat != 2 || lon != 2) {
                // асинхронный запрос на прогноз
                Call<Forecast7DaysResponse> callForecast = jsonPlaceHolderApi.get7DaysForecast(lat, lon, "hourly", "ru", "metric");
                callForecast.enqueue(new Callback<Forecast7DaysResponse>() {
                    @Override
                    public void onResponse(Call<Forecast7DaysResponse> call, Response<Forecast7DaysResponse> response) {
                        //обновляем в базу
                        Forecast7DaysResponse val = response.body();
                        if (response.code() == 200)
                            if (mViewModelFav!=null) upsertViewModelForecast(mViewModelFav, new Forecast7Days(val.getDaily()));
                            else upsertViewModelForecast(mViewModelSearch, new Forecast7Days(val.getDaily()));

//                            mViewModel.upsert(new Forecast7Days(val.getDaily()));
                        Navigation.findNavController(view).navigate(R.id.currentWeatherFragment);
                    }

                    @Override
                    public void onFailure(Call<Forecast7DaysResponse> call, Throwable t) {
                        showSnackBar("Что-то пошло не так. Попробуйте снова");
                    }
                });
            }

        } else showSnackBar("Нет интернет соединения");
    }



    private void showSnackBar(String text) {
        Snackbar snack = Snackbar.make(view, text, Snackbar.LENGTH_LONG);
        View view = snack.getView();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
        params.gravity = Gravity.TOP;
        view.setLayoutParams(params);
        snack.show();
    }

    private  void upsertViewModelForecast(SearchWeatherViewModel mViewModel, Forecast7Days forecast7Days) {
        mViewModel.upsert(forecast7Days);
    }
    private  void upsertViewModelForecast(FavouriteWeatherViewModel mViewModel, Forecast7Days forecast7Days) {
        mViewModel.upsert(forecast7Days);
    }

    private void upsertViewModelCurrentWeather(SearchWeatherViewModel mViewModel, CurrentWeather currentWeather) {
        mViewModel.upsert(currentWeather);
    }

    private void upsertViewModelCurrentWeather(FavouriteWeatherViewModel mViewModel, CurrentWeather currentWeather) {
        mViewModel.upsert(currentWeather);
    }
}
