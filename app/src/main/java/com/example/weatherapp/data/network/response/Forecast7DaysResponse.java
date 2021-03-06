package com.example.weatherapp.data.network.response;

import com.example.weatherapp.data.db.entity.forecast7Days.ForecastDaily;

import java.util.List;

public class Forecast7DaysResponse {
    List<ForecastDaily> forecastDaily;

    public Forecast7DaysResponse(List<ForecastDaily> forecastDaily) {
        this.forecastDaily = forecastDaily;
    }

    public List<ForecastDaily> getForecastDaily() {
        return forecastDaily;
    }
}
