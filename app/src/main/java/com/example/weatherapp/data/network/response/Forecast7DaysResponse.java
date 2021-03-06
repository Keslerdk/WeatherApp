package com.example.weatherapp.data.network.response;

import com.example.weatherapp.data.db.entity.forecast7Days.ForecastDaily;

import java.util.List;

public class Forecast7DaysResponse {
    List<ForecastDaily> daily;

    public Forecast7DaysResponse(List<ForecastDaily> forecastDaily) {
        this.daily = forecastDaily;
    }

    public List<ForecastDaily> getDaily() {
        return daily;
    }
}
