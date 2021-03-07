package com.example.weatherapp.ui.recyclerViews;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.R;

import java.util.List;

public class FavouriteRecyclerAdapter extends RecyclerView.Adapter<FavouriteRecyclerAdapter.FavouriteRecuclerHolder> {

    List<FavouriteItem> favouriteItemList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public FavouriteRecyclerAdapter(List<FavouriteItem> favouriteItemList) {
        this.favouriteItemList = favouriteItemList;
    }

    @NonNull
    @Override
    public FavouriteRecuclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favourite_card, parent, false);
        FavouriteRecuclerHolder frh = new FavouriteRecuclerHolder(view, mListener);
        return frh;
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteRecuclerHolder holder, int position) {
        FavouriteItem currentItem = favouriteItemList.get(position);

//        holder.iconFav.setImageResource(currentItem.getImage());
        holder.cityNameFav.setText(currentItem.getCityName());
        holder.dayTempFav.setText(String.valueOf(currentItem.getTemp()));
        holder.feelsLikeFav.setText(String.valueOf(currentItem.getFeels_like()));
        holder.windFav.setText(String.valueOf(currentItem.getWind()));

        boolean isExpanded = favouriteItemList.get(position).isExpended();
        holder.expandable.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return favouriteItemList.size();
    }

    public class FavouriteRecuclerHolder extends RecyclerView.ViewHolder {
        private ImageView iconFav;
        private TextView cityNameFav;
        private TextView dayTempFav;
        private TextView feelsLikeFav;
        private TextView windFav;
        private ConstraintLayout expandable;
        private ImageView starFav;
        private ImageView expandableMenu;

        public FavouriteRecuclerHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            iconFav = itemView.findViewById(R.id.iconFav);
            cityNameFav = itemView.findViewById(R.id.cityNameFav);
            dayTempFav = itemView.findViewById(R.id.tempFav);
            feelsLikeFav = itemView.findViewById(R.id.feelLikeFav);
            windFav = itemView.findViewById(R.id.windFav);
            expandable = itemView.findViewById(R.id.expandable);
            starFav = itemView.findViewById(R.id.starFav);
            expandableMenu = itemView.findViewById(R.id.expanded_menu);

            expandableMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    FavouriteItem favouriteItem = favouriteItemList.get(getAdapterPosition());
                    if (favouriteItem.isExpended()) {
                        expandableMenu.setImageResource(R.drawable.ic_expand_less);
                        favouriteItem.setExpended(false);
                        notifyItemChanged(getAdapterPosition());
                    } else {
                        expandableMenu.setImageResource(R.drawable.ic_expand_more);
                        favouriteItem.setExpended(true);
                        notifyItemChanged(getAdapterPosition());
                    }
//                    favouriteItem.setExpended(!favouriteItem.isExpended());
//                    notifyItemChanged(getAdapterPosition());
                }
            });

            starFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    starFav.setImageResource(R.drawable.ic_favourite);
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
        }
    }
}