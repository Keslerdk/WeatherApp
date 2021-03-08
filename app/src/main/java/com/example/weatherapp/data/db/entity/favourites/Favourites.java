package com.example.weatherapp.data.db.entity.favourites;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "favourites_table")
public class Favourites {
    @PrimaryKey(autoGenerate = true)
    public int id;

//    public int iconFav;
    public String nameCity;
    public String descriptionFav;
    public float feelLikeFav;
    public float tempFav;
    public float windFav;
    public float humidityFav;

    @Ignore
    public boolean expended;

    public Favourites(String nameCity, String descriptionFav, float feelLikeFav, float tempFav,
                      float windFav, float humidityFav) {
        this.nameCity = nameCity;
        this.descriptionFav = descriptionFav;
        this.feelLikeFav = feelLikeFav;
        this.tempFav = tempFav;
        this.windFav = windFav;
        this.humidityFav = humidityFav;

        this.expended = false;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameCity() {
        return nameCity;
    }

    public String getDescriptionFav() {
        return descriptionFav;
    }

    public float getFeelLikeFav() {
        return feelLikeFav;
    }

    public float getTempFav() {
        return tempFav;
    }

    public float getWindFav() {
        return windFav;
    }

    public float getHumidityFav() {
        return humidityFav;
    }

//    public int getIconFav() {
//        return iconFav;
//    }



    public boolean isExpended() {
        return expended;
    }

    public void setExpended(boolean expended) {
        this.expended = expended;
    }
}
