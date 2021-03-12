package com.example.weatherapp.ui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.weatherapp.R;
import com.example.weatherapp.ui.map.GoogleMaps;
import com.example.weatherapp.ui.map.YandexMaps;

import java.util.List;

public class MapDialog extends AppCompatDialogFragment {

    private Intent intent;

    private float lat;
    private float lon;
    private String cityName;

    public MapDialog(float lat, float lon, String cityName) {
        this.lat = lat;
        this.lon = lon;
        this.cityName = cityName;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog alertDialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

//        intent.putExtra("lat", lat);
//        intent.putExtra("lon", lon);
//        intent.putExtra("cityName", cityName);

//        LayoutInflater inflater = getActivity().getLayoutInflater();
//        View view = inflater.inflate(R.layout.layout_dialog, null);

        CharSequence[] values = {"Google Map", "Yandex Map"};
        builder.setTitle("Choose maps")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("next", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (intent!=null) startActivity(intent);
                        else Toast.makeText(getContext(), "Intent is null", Toast.LENGTH_SHORT).show();
                    }
                })
                .setSingleChoiceItems(values, 1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("Dialog", String.valueOf(which));
                        if (which==0) {
                            intent = new Intent(getContext(), GoogleMaps.class);
                            intent.putExtra("lat", lat);
                            intent.putExtra("lon", lon);
                            intent.putExtra("cityName", cityName);
                        } else {
                            intent = new Intent(getContext(), YandexMaps.class);
                            intent.putExtra("lat", lat);
                            intent.putExtra("lon", lon);
                            intent.putExtra("cityName", cityName);
                        }

                    }
                });
        alertDialog = builder.create();
        alertDialog.show();
        return alertDialog;
    }

}
