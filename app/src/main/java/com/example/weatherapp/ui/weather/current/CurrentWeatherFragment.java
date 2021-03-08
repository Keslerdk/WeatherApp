package com.example.weatherapp.ui.weather.current;

import androidx.lifecycle.Observer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherapp.R;
import com.example.weatherapp.data.db.entity.currentWeather.CurrentWeather;
import com.example.weatherapp.data.db.entity.favourites.Favourites;

import java.util.List;

public class CurrentWeatherFragment extends Fragment {

    public static CurrentWeatherFragment newInstance() {
        return new CurrentWeatherFragment();
    }

    private CurrentWeatherViewModel mViewModel;

    private TextView cityName;
    private TextView tempCur;
    private TextView feels_likeCur;
    private TextView humidityCur;
    private TextView windCur;
    private ImageView starCur;

    private boolean isFavourite = false;
    Favourites currentItem;
    List<Favourites> favouritesMain;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.current_weather_fragment, container, false);

        cityName = view.findViewById(R.id.cityName);
        tempCur = view.findViewById(R.id.tempCur);
        feels_likeCur = view.findViewById(R.id.feelLikeCur);
        humidityCur = view.findViewById(R.id.humidityCur);
        windCur = view.findViewById(R.id.windCur);
        starCur = view.findViewById(R.id.starCur);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(CurrentWeatherViewModel.class);
//        mViewModel = new ViewModelProvider(this).get(CurrentWeatherViewModel.class);
        // TODO: Use the ViewModel

        mViewModel.getFavourites().observe(getViewLifecycleOwner(), new Observer<List<Favourites>>() {
            @Override
            public void onChanged(List<Favourites> favourites) {
                favouritesMain = favourites;
            }
        });

        mViewModel.getCurrentWeather().observe(getViewLifecycleOwner(), new Observer<CurrentWeather>() {
            @Override
            public void onChanged(CurrentWeather currentWeather) {
                currentItem = new Favourites(currentWeather.getName(), currentWeather.getWeather().getDescription(),
                        currentWeather.getMain().getFeels_like(), currentWeather.getMain().getTemp(),
                        currentWeather.getWind().getSpeed(), currentWeather.getMain().getHumidity());
                currentItem.setId(currentWeather.getIdCity());

                cityName.setText(currentWeather.getName());
                tempCur.setText(String.valueOf(currentWeather.getMain().getTemp()));
                feels_likeCur.setText(String.valueOf(currentWeather.getMain().getFeels_like()));
                humidityCur.setText(String.valueOf(currentWeather.getMain().getHumidity()));
                windCur.setText(String.valueOf(currentWeather.getWind().getSpeed()));

                Toast.makeText(getContext(), currentWeather.name, Toast.LENGTH_SHORT).show();
            }
        });

        starCur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFavourite) {
                    isFavourite=true;
                    starCur.setImageResource(R.drawable.ic_star_yellow);
                    mViewModel.insert(currentItem);
                } else {
                    isFavourite=false;
                    starCur.setImageResource(R.drawable.ic_favourite);
                    mViewModel.delete(favouritesMain.get(favouritesMain.size()-1));
                }

            }
        });
    }
}