package com.example.weatherapp.ui.setting.favourite;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.weatherapp.R;
import com.example.weatherapp.data.db.entity.currentWeather.CurrentWeather;
import com.example.weatherapp.data.db.entity.favourites.Favourites;
import com.example.weatherapp.data.db.entity.forecast7Days.Forecast7Days;
import com.example.weatherapp.data.network.WeatherApiRequest;
import com.example.weatherapp.data.network.response.CurrentWeatherResponse;
import com.example.weatherapp.data.network.response.Forecast7DaysResponse;
import com.example.weatherapp.ui.recyclerViews.FavouriteRecyclerAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavouriteWeatherFragment extends Fragment {

    private FavouriteWeatherViewModel mViewModel;

    public static FavouriteWeatherFragment newInstance() {
        return new FavouriteWeatherFragment();
    }

    private RecyclerView favRecyclerView;
    private FavouriteRecyclerAdapter favAdapter;
    private RecyclerView.LayoutManager favLayoutManager;

    private ConstraintLayout nullFav;

    float lat;
    float lon;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favourite_weather_fragment, container, false);
        nullFav = view.findViewById(R.id.nullFav);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(FavouriteWeatherViewModel.class);
//        mViewModel = new ViewModelProvider(this).get(FavouriteWeatherViewModel.class);
        // TODO: Use the ViewModel

        initRecyclerView(getView());

    }

    private void initRecyclerView(View view) {
        mViewModel.getFavourites().observe(getViewLifecycleOwner(), new Observer<List<Favourites>>() {
            @Override
            public void onChanged(List<Favourites> favourites) {
                favRecyclerView = view.findViewById(R.id.favRecyclerView);
                favLayoutManager = new LinearLayoutManager(getContext());
                favAdapter = new FavouriteRecyclerAdapter(favourites);
                favRecyclerView.setLayoutManager(favLayoutManager);
                favRecyclerView.setAdapter(favAdapter);

                favAdapter.setOnItemClickListener(new FavouriteRecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        firstApiCall(favourites.get(position).getNameCity());

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                secondApiCall();
                            }
                        }, 5000);
                    }

                    @Override
                    public void onDeleteClick(int position) {
                        favAdapter.notifyItemRemoved(position);
                        mViewModel.delete(favourites.get(position));
                        //поменять звездочку в текущей погоде.
                        mViewModel.updateIsFavourite(false);
                    }
                });
                if (favourites == null || favourites.isEmpty()) nullFav.setVisibility(View.VISIBLE);
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