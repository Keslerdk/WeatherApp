package com.example.weatherapp.ui.weather.current;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherapp.R;
import com.example.weatherapp.data.db.entity.currentWeather.CurrentWeather;
import com.example.weatherapp.data.db.entity.favourites.Favourites;
import com.example.weatherapp.ui.dialogs.MapDialog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class CurrentWeatherFragment extends Fragment {

    public static CurrentWeatherFragment newInstance() {
        return new CurrentWeatherFragment();
    }

    private CurrentWeatherViewModel mViewModel;

    private TextView cityName;
    private TextView dataCur;
    private TextView tempCur;
    private TextView feels_likeCur;
    private TextView descriptionCur;
    private TextView humidityCur;
    private TextView windCur;
    private ImageView starCur;
    private ImageView iconCur;
    private ConstraintLayout nullCur;
    private Button mapBtn;

    private boolean isFavourite = false;
    float lat;
    float lon;
    String cityVal;
    Favourites currentItem;
    List<Favourites> favouritesMain;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.current_weather_fragment, container, false);

        cityName = view.findViewById(R.id.cityName);
        dataCur = view.findViewById(R.id.dataCur);
        tempCur = view.findViewById(R.id.tempCur);
        feels_likeCur = view.findViewById(R.id.feelLikeCur);
        descriptionCur = view.findViewById(R.id.descriptionCur);
        humidityCur = view.findViewById(R.id.humidityCur);
        windCur = view.findViewById(R.id.windCur);
        starCur = view.findViewById(R.id.starCur);
        iconCur = view.findViewById(R.id.curWeatherIcon);
        nullCur = view.findViewById(R.id.nullCur);
        mapBtn = view.findViewById(R.id.mapBtn);
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
                if (currentWeather != null) {
                    int icon = getImageid(getContext(), "w" + currentWeather.getWeather().getIcon());
                    currentItem = new Favourites(icon, currentWeather.getName(), currentWeather.getWeather().getDescription(),
                            currentWeather.getMain().getFeels_like(), currentWeather.getMain().getTemp(),
                            currentWeather.getWind().getSpeed(), currentWeather.getMain().getHumidity());
                    currentItem.setId(currentWeather.getIdCity());

                    lat =  currentWeather.getCoordCurrentWeather().getLat();
                    lon =  currentWeather.getCoordCurrentWeather().getLon();
                    cityVal=currentWeather.getName();

                    Date date = new Date(currentWeather.getDt() * 1000L);
                    SimpleDateFormat f = new SimpleDateFormat("EEEE, HH:mm");
                    f.setTimeZone(TimeZone.getTimeZone("GMT+3"));

                    cityName.setText(currentWeather.getName());
                    dataCur.setText(f.format(date));
                    tempCur.setText(String.valueOf(((int) (currentWeather.getMain().getTemp() * 10)) / 10.0) + "°C");
                    feels_likeCur.setText(String.valueOf(((int) (currentWeather.getMain().getFeels_like() * 10)) / 10.0) + "°C");
                    descriptionCur.setText(currentWeather.getWeather().getDescription());
                    humidityCur.setText(String.valueOf((int) (currentWeather.getMain().getHumidity())) + "%");
                    windCur.setText(String.valueOf((int) currentWeather.getWind().getSpeed()) + " км/ч");
                    iconCur.setImageResource(getImageid(getContext(), "w" + currentWeather.
                            getWeather().getIcon()));
                    //TODO: скачать ночные картинки
                    Toast.makeText(getContext(), String.valueOf(currentWeather.isFavourite()), Toast.LENGTH_SHORT).show();
                } else {
                    nullCur.setVisibility(View.VISIBLE);
                }
            }
        });

        starCur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFavourite) {
                    isFavourite = true;
                    starCur.setImageResource(R.drawable.ic_star_yellow);
                    mViewModel.insert(currentItem);
                    //TODO: отображение желтой звездочки через isFavourite()
                } else {
                    isFavourite = false;
                    starCur.setImageResource(R.drawable.ic_favourite);
                    mViewModel.delete(favouritesMain.get(favouritesMain.size() - 1));
                }

            }
        });

        //кнопка для перехода на карты
        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //создание всплывающего окна
                Log.d("on click", String.valueOf(lat));
                MapDialog exampleDialog = new MapDialog(lat, lon, cityVal);
                exampleDialog.show(getFragmentManager(), "example dialog");
            }
        });

    }

    public static int getImageid(Context context, String imageName) {
        return context.getResources().getIdentifier("drawable/" + imageName, null,
                context.getPackageName());
    }
}