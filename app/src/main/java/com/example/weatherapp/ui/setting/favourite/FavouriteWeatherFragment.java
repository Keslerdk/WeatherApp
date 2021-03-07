package com.example.weatherapp.ui.setting.favourite;

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
import com.example.weatherapp.ui.recyclerViews.FavouriteItem;
import com.example.weatherapp.ui.recyclerViews.FavouriteRecyclerAdapter;
import com.example.weatherapp.ui.recyclerViews.ForecastRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class FavouriteWeatherFragment extends Fragment {

    private FavouriteWeatherViewModel mViewModel;

    public static FavouriteWeatherFragment newInstance() {
        return new FavouriteWeatherFragment();
    }

    private List<FavouriteItem> favouriteItemList = new ArrayList<>();
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
        mViewModel = new ViewModelProvider(this).get(FavouriteWeatherViewModel.class);
        // TODO: Use the ViewModel
        favouriteItemList.add(new FavouriteItem("Moscow", 1, 2, 100));
        initRecyclerView(getView());




    }

    private void initRecyclerView(View view) {
        favRecyclerView = view.findViewById(R.id.favRecyclerView);
        favLayoutManager = new LinearLayoutManager(getContext());
        favAdapter = new FavouriteRecyclerAdapter(favouriteItemList);
        favRecyclerView.setLayoutManager(favLayoutManager);
        favRecyclerView.setAdapter(favAdapter);

        favAdapter.setOnItemClickListener(new FavouriteRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }
        });
    }

    public void removeItem(int position) {
        favouriteItemList.remove(position);
        favAdapter.notifyItemRemoved(position);
    }
}