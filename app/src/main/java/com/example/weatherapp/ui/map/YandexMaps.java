package com.example.weatherapp.ui.map;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.weatherapp.R;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.runtime.image.ImageProvider;

public class YandexMaps extends AppCompatActivity {

    private MapView map;
    private float lat;
    private float lon;
    String cityName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        MapKitFactory.setApiKey("36cbbd87-ea5a-4077-932f-132766bd23c3");
        MapKitFactory.initialize(this);
        setContentView(R.layout.activity_yandex_maps);

        super.onCreate(savedInstanceState);

        lat = getIntent().getFloatExtra("lat", 0);
        lon = getIntent().getFloatExtra("lon", 0);
        cityName = getIntent().getStringExtra("cityName");


        map = findViewById(R.id.yandexmapFrag);


        map.getMap().move(new CameraPosition(new Point(lat, lon), 11.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0),
                null);
        map.getMap().getMapObjects().addPlacemark(new Point(lat, lon)
                , ImageProvider.fromResource(this, R.drawable.placemarker3));

//        PlacemarkMapObject mark = mapObjectCollection.addPlacemark(new Point(55.751574, 37.573856));
//        mark.setOpacity(0.5f);
//        mark.setIcon(ImageProvider.fromResource(this, R.drawable.ic_placemark));
//        mark.setDraggable(true);

       
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