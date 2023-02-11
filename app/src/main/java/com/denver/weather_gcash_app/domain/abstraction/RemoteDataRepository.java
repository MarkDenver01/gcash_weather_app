package com.denver.weather_gcash_app.domain.abstraction;

import com.denver.weather_gcash_app.data.response.current_weathers.CurrentWeatherResponse;

import io.reactivex.Observable;

public interface RemoteDataRepository {
    Observable<CurrentWeatherResponse> getCurrentWeather(String lat, String lon, String apiKey);
}
