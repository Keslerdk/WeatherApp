package com.example.weatherapp.ui.weather.future;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weatherapp.R;
import com.example.weatherapp.data.db.entity.forecast7Days.Forecast7Days;
import com.example.weatherapp.ui.recyclerViews.Forecast7DaysItem;
import com.example.weatherapp.ui.recyclerViews.ForecastRecyclerAdapter;
import com.example.weatherapp.ui.setting.search.SearchWeatherViewModel;

import java.util.ArrayList;
import java.util.List;

public class Forecast7DaysFragment extends Fragment {

    private Forecast7DaysViewModel mViewModel;

    //обработка ресайклер вью
    private RecyclerView forecastRecyclerView;
    private RecyclerView.Adapter forecastAdapter;
    private RecyclerView.LayoutManager forecastLayoutManager;

    //список с карточками
    ArrayList<Forecast7DaysItem> exampleList = new ArrayList<>();

    public static Forecast7DaysFragment newInstance() {
        return new Forecast7DaysFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.forecast7_days_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(Forecast7DaysViewModel.class);
//        mViewModel = new ViewModelProvider(this).get(Forecast7DaysViewModel.class);
        // TODO: Use the ViewModel
        mViewModel.getForecast7Days().observe(getViewLifecycleOwner(), new Observer<Forecast7Days>() {
            @Override
            public void onChanged(Forecast7Days forecast7Days) {
                for (int i = 0; i < 7; i++) {
                    exampleList.add(new Forecast7DaysItem(String.valueOf(forecast7Days.getForecastDaily().get(i).getTempForecast().getDay()),
                            String.valueOf(forecast7Days.getForecastDaily().get(i).getFeelsLikeForecast().getDay())));
                }
                buildrecyclerView(getView());
            }
        });
    }

    private void buildrecyclerView(View view) {
        forecastRecyclerView = view.findViewById(R.id.forecastRecyclerView);
        forecastRecyclerView.setHasFixedSize(true);
        forecastLayoutManager = new LinearLayoutManager(getContext());
        forecastAdapter = new ForecastRecyclerAdapter(exampleList);
        forecastRecyclerView.setLayoutManager(forecastLayoutManager);
        forecastRecyclerView.setAdapter(forecastAdapter);
    }


}