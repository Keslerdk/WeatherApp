package com.example.weatherapp.ui.recyclerViews;

public class FavouriteItem {
//    int image;
    String cityName;
    String description;
    float temp;
    float feels_like;
//    float clouds;
//    float humidity;
    float wind;

    boolean expended;

    public FavouriteItem(String cityName, float temp, float feels_like, float wind) {
//        this.image = image;
        this.cityName = cityName;
        this.temp = temp;
        this.feels_like = feels_like;
//        this.clouds = clouds;
//        this.humidity = humidity;
        this.wind = wind;
        this.expended = false;
    }

//    public int getImage() {
//        return image;
//    }

    public String getCityName() {
        return cityName;
    }

    public float getTemp() {
        return temp;
    }

    public float getFeels_like() {
        return feels_like;
    }

//    public float getClouds() {
//        return clouds;
//    }
//
//    public float getHumidity() {
//        return humidity;
//    }

    public float getWind() {
        return wind;
    }

    public boolean isExpended() {
        return expended;
    }

    public void setExpended(boolean expended) {
        this.expended = expended;
    }
}
