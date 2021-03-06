package com.example.weatherapp.ui.weather.future;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weatherapp.R;
import com.example.weatherapp.ui.Forecast7DaysItem;
import com.example.weatherapp.ui.adapters.ForecastRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class Forecast7DaysFragment extends Fragment {

    private Forecast7DaysViewModel mViewModel;

    private RecyclerView forecastRecyclerView;
    private RecyclerView.Adapter forecastAdapter;
    private RecyclerView.LayoutManager forecastLayoutManager;
    ArrayList<Forecast7DaysItem> exampleList = new ArrayList<>();

    public static Forecast7DaysFragment newInstance() {
        return new Forecast7DaysFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        
        creatRecyclerView();
//
        View view = inflater.inflate(R.layout.forecast7_days_fragment, container, false);

        buildrecyclerView(view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(Forecast7DaysViewModel.class);
        // TODO: Use the ViewModel

    }
    
    private void buildrecyclerView(View view) {
        forecastRecyclerView = view.findViewById(R.id.forecastRecyclerView);
        forecastRecyclerView.setHasFixedSize(true);
        forecastLayoutManager = new LinearLayoutManager(getContext());
        forecastAdapter = new ForecastRecyclerAdapter(exampleList);
        forecastRecyclerView.setLayoutManager(forecastLayoutManager);
        forecastRecyclerView.setAdapter(forecastAdapter);
    }
    
    private void  creatRecyclerView () {
        List<String> weekDays = new ArrayList<>();
        weekDays.add("Понедельник");
        weekDays.add("Вторник");
        weekDays.add("Среда");
        weekDays.add("Четверг");
        weekDays.add("Пятница");
        weekDays.add("Суббота");
        weekDays.add("Воскресенье");

        for (String val : weekDays) exampleList.add(new Forecast7DaysItem(R.drawable.ic_7days, "Line 1", "Line 2"));
    }

}