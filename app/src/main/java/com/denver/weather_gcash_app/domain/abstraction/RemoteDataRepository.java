package com.denver.weather_gcash_app.domain.abstraction;

import com.denver.weather_gcash_app.data.response.current_weathers.CurrentWeatherResponse;
import com.denver.weather_gcash_app.data.response.forecast_weathers.ForecastWeatherResponse;
import com.google.gson.JsonObject;

import io.reactivex.Observable;

public interface RemoteDataRepository {
    Observable<CurrentWeatherResponse> getCurrentWeather(String lat, String lon, String apiKey);

    Observable<CurrentWeatherResponse> getCurrentWeatherByPlace(String q, String apiKey);

    Observable<ForecastWeatherResponse> getForecastWeather(int id, String apiKey);
}
