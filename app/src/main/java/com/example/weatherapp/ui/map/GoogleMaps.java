package com.example.weatherapp.ui.map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.example.weatherapp.R;
import com.google.android.gms.maps.OnMapReadyCallback;

public class GoogleMap extends FragmentActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map);
    }

    @Override
    public void onMapReady(com.google.android.gms.maps.GoogleMap googleMap) {

    }
}