package com.example.weatherapp.ui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.weatherapp.R;

import java.util.List;

public class MapDialog extends AppCompatDialogFragment {
    private RadioButton radiobtnGoogle;
    private RadioButton radiobtnYandex;
    private RadioGroup radioGroup;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog alertDialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.layout_dialog, null);
//
//        radiobtnGoogle = view.findViewById(R.id.radioBtnGoogle);
//        radiobtnYandex = view.findViewById(R.id.radioBtnYandex);
//        radioGroup = view.findViewById(R.id.radioGroup);

//        int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
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

                    }
                })
                .setSingleChoiceItems(values, 1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        alertDialog = builder.create();
        alertDialog.show();
        return alertDialog;
    }

}
