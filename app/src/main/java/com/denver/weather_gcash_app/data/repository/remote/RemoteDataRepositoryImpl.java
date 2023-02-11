package com.denver.weather_gcash_app.data.repository.remote;

import com.denver.weather_gcash_app.data.remote.client.ApiService;
import com.denver.weather_gcash_app.domain.abstraction.RemoteDataRepository;
import com.denver.weather_gcash_app.data.response.current_weathers.CurrentWeatherResponse;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class RemoteDataRepositoryImpl implements RemoteDataRepository {
    private ApiService apiService;

    @Inject
    public RemoteDataRepositoryImpl(ApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public Observable<CurrentWeatherResponse> getCurrentWeather(String lat, String lon, String apiKey) {
        return apiService.getCurrentWeather(lat, lon, apiKey);
    }
}
