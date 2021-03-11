package com.example.weatherapp.ui.map;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.weatherapp.R;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.mapview.MapView;

public class YandexMaps extends AppCompatActivity {

    private MapView map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MapKitFactory.setApiKey("799c7d30-e06a-4df6-89c7-950b9c262884");
        MapKitFactory.initialize(this);

        setContentView(R.layout.activity_yandex_maps);

        map = findViewById(R.id.yandexmapFrag);
        map.getMap().move(new CameraPosition(new Point(55.751574, 37.573856), 11.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0),
                null);
        map.getMap().getMapObjects().addPlacemark(new Point(55.751574, 37.573856));
       
    }

    @Override
    protected void onStop() {
        super.onStop();
        map.onStop();
        MapKitFactory.getInstance().onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        map.onStart();
        MapKitFactory.getInstance().onStart();
    }
}