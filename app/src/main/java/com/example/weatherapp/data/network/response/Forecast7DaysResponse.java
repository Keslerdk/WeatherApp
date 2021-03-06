package com.example.weatherapp.data.network.response;

import java.util.List;

public class Forecast7DaysResponse {
    List<ForecastDaily> forecastDaily;

    public Forecast7DaysResponse(List<ForecastDaily> forecastDaily) {
        this.forecastDaily = forecastDaily;
    }
}
