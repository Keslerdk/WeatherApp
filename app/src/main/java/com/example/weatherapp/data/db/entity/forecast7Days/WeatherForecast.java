package com.example.weatherapp.data.db.entity.forecast7Days;

public class WeatherForecast {
    public int id;
    public String main;
    public String description;
    public String icon;

    public WeatherForecast(int id, String main, String descroption, String icon) {
        this.id = id;
        this.main = main;
        this.description = descroption;
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public String getMain() {
        return main;
    }

    public String getDescroption() {
        return description;
    }

    public String getIcon() {
        return icon;
    }
}
