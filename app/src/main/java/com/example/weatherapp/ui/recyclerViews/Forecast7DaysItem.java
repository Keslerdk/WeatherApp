package com.example.weatherapp.ui.recyclerViews;


//обработка полей в карточке forecast7days
public class Forecast7DaysItem {
    int imageIcon;
    float temp;
    float feelsLiketemp;
    String description;
    String date;

    public Forecast7DaysItem(int imageIcon,float temp, float feelsLiketemp,String description, String date) {
        this.imageIcon = imageIcon;
        this.temp = temp;
        this.feelsLiketemp = feelsLiketemp;
        this.description=description;
        this.date=date;
    }

    public int getImageIcon() {
        return imageIcon;
    }

    public float getTemp() {
        return temp;
    }

    public float getFeelsLiketemp() {
        return feelsLiketemp;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }
}
