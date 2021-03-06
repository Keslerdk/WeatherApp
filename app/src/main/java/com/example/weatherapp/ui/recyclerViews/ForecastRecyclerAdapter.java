package com.example.weatherapp.ui.recyclerViews;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.R;

import java.util.ArrayList;

public class ForecastRecyclerAdapter extends RecyclerView.Adapter<ForecastRecyclerAdapter.ForecastViewHolder> {

    private ArrayList<Forecast7DaysItem> mforecast7DaysItems;

    public ForecastRecyclerAdapter(ArrayList<Forecast7DaysItem> mforecast7DaysItems) {
        this.mforecast7DaysItems = mforecast7DaysItems;
    }

    @NonNull
    @Override
    public ForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forecast_card, parent,
                false);
        ForecastViewHolder fvh = new ForecastViewHolder(view);
        return fvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastViewHolder holder, int position) {
        Forecast7DaysItem currentItem = mforecast7DaysItems.get(position);

        holder.forecastIcon.setImageResource(currentItem.getImageIcon());
        holder.dayTemp.setText(currentItem.getTemp());
        holder.feelsLikeTemp.setText(currentItem.getFeelsLiketemp());
    }

    @Override
    public int getItemCount() {
        return mforecast7DaysItems.size();
    }

    public class ForecastViewHolder extends RecyclerView.ViewHolder {
        public ImageView forecastIcon;
        public TextView dayTemp;
        public TextView feelsLikeTemp;
        public ForecastViewHolder(@NonNull View itemView) {
            super(itemView);
            forecastIcon = itemView.findViewById(R.id.forecastIcon);
            dayTemp =  itemView.findViewById(R.id.dayTemp);
            feelsLikeTemp = itemView.findViewById(R.id.feelLikeTemp);
        }
    }
}
