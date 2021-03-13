package com.example.weatherapp.ui.recyclerViews;


//обработка полей в карточке forecast7days
public class Forecast7DaysItem {
    int imageIcon;
    String temp;
    String feelsLiketemp;

    public Forecast7DaysItem(int imageIcon,String temp, String feelsLiketemp) {
        this.imageIcon = imageIcon;
        this.temp = temp;
        this.feelsLiketemp = feelsLiketemp;
    }

    public int getImageIcon() {
        return imageIcon;
    }

    public String getTemp() {
        return temp;
    }

    public String getFeelsLiketemp() {
        return feelsLiketemp;
    }
}
