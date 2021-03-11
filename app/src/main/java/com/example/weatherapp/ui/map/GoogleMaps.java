package com.example.weatherapp.ui.map;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.weatherapp.R;
import com.example.weatherapp.ui.setting.search.SearchWeatherFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class GoogleMaps extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap map;

    private float lat;
    private float lon;
    String cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map);

        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.googlemapRfagment);

        supportMapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(com.google.android.gms.maps.GoogleMap googleMap) {
        map = googleMap;

        lat = getIntent().getFloatExtra("lat", 0);
        lon = getIntent().getFloatExtra("lon", 0);
        cityName = getIntent().getStringExtra("cityName");

        Log.d("Google Maps 1", String.valueOf(lat));

        LatLng novogorsk = new LatLng(lat, lon);
        map.addMarker(new MarkerOptions().position(novogorsk).title(cityName));
        map.moveCamera(CameraUpdateFactory.newLatLng(novogorsk));
    }
}