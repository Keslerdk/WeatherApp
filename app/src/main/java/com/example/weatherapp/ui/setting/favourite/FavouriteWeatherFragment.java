package com.example.weatherapp.ui.setting.favourite;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

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
import com.example.weatherapp.data.db.entity.favourites.Favourites;
import com.example.weatherapp.ui.recyclerViews.FavouriteItem;
import com.example.weatherapp.ui.recyclerViews.FavouriteRecyclerAdapter;
import com.example.weatherapp.ui.recyclerViews.ForecastRecyclerAdapter;
import com.example.weatherapp.ui.setting.search.SearchWeatherViewModel;

import java.util.ArrayList;
import java.util.List;

public class FavouriteWeatherFragment extends Fragment {

    private FavouriteWeatherViewModel mViewModel;

    public static FavouriteWeatherFragment newInstance() {
        return new FavouriteWeatherFragment();
    }

    private RecyclerView favRecyclerView;
    private FavouriteRecyclerAdapter favAdapter;
    private RecyclerView.LayoutManager favLayoutManager;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favourite_weather_fragment, container, false);
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
                    public void onDeleteClick(int position) {
                        mViewModel.delete(favourites.get(position));
                        favAdapter.notifyItemRemoved(position);
                    }
                });
            }
        });




    }

}