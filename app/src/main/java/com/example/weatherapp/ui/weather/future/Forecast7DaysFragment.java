package com.example.weatherapp.ui.weather.future;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weatherapp.R;
import com.example.weatherapp.data.db.entity.forecast7Days.Forecast7Days;
import com.example.weatherapp.ui.recyclerViews.Forecast7DaysItem;
import com.example.weatherapp.ui.recyclerViews.ForecastRecyclerAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

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

    private ConstraintLayout nullForecast;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.forecast7_days_fragment, container, false);
        nullForecast = view.findViewById(R.id.nullForecast);
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
                if (forecast7Days != null) {
                    for (int i = 1; i < 8; i++) {
                        int icon = getImageid(getContext(), "w" + forecast7Days.getForecastDaily().get(i).getWeather().get(0).getIcon());
                        Date date = new Date(forecast7Days.getForecastDaily().get(i).getDt() * 1000L);
                        SimpleDateFormat f = new SimpleDateFormat("EEEE, d.MM");
                        f.setTimeZone(TimeZone.getTimeZone("GMT+3"));
                        Log.d("weekDay", f.format(date));
                        exampleList.add(new Forecast7DaysItem(icon, forecast7Days.getForecastDaily().get(i).getTempForecast().getDay(),
                                forecast7Days.getForecastDaily().get(i).getFeelsLikeForecast().getDay(),
                                forecast7Days.getForecastDaily().get(i).getWeather().get(0).getDescroption(), f.format(date)));
                    }
                    buildrecyclerView(getView());
                } else nullForecast.setVisibility(View.VISIBLE);
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

    public static int getImageid(Context context, String imageName) {
        return context.getResources().getIdentifier("drawable/" + imageName, null,
                context.getPackageName());
    }


}