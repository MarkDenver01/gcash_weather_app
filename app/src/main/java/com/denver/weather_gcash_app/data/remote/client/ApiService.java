package com.denver.weather_gcash_app.data.remote.client;

import com.denver.weather_gcash_app.data.response.current_weathers.CurrentWeatherResponse;
import com.denver.weather_gcash_app.data.response.forecast_weathers.ForecastWeatherResponse;
import com.google.gson.JsonObject;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("weather")
    Observable<CurrentWeatherResponse> getCurrentWeather(
            @Query("lat") String lat,
            @Query("lon") String lon,
            @Query("appid") String appId);

    @GET("weather")
    Observable<CurrentWeatherResponse> getCurrentWeatherByPlace(
            @Query("q") String queryPlace,
            @Query("appid") String apiKey);

    @GET("forecast")
    Observable<ForecastWeatherResponse> getForecastWeather(
            @Query("id") int id,
            @Query("appid") String appId);
}
