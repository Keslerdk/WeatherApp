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
import com.example.weatherapp.data.network.request.ApiCalls;
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
                        ApiCalls apiCalls = new ApiCalls(getContext(), getView(), mViewModel, getActivity());
                        apiCalls.getWeather(favourites.get(position).getNameCity());
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
}