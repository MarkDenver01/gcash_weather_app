package com.denver.weather_gcash_app.data.remote.client;

import com.denver.weather_gcash_app.data.response.current_weathers.CurrentWeatherResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("weather")
    Observable<CurrentWeatherResponse> getCurrentWeather(
            @Query("lat") String lat,
            @Query("lon") String lon,
            @Query("appid") String appId);


}
