package com.denver.weather_gcash_app.presentation.viewmodel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.denver.weather_gcash_app.data.remote.client.ApiClient;
import com.denver.weather_gcash_app.data.repository.MainRepositoryImpl;
import com.denver.weather_gcash_app.data.response.current_weathers.CurrentWeatherResponse;
import com.denver.weather_gcash_app.data.response.forecast_weathers.ForecastWeatherResponse;
import com.denver.weather_gcash_app.presentation.base.MainViewModel;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class WeatherViewModel extends MainViewModel {
    private static final String TAG = "CURRENT_WEATHER";
    private MutableLiveData<CurrentWeatherResponse> currentWeatherMutableLiveData;
    private MutableLiveData<ForecastWeatherResponse> forecastWeatherMutableLiveData;

    @Inject
    public WeatherViewModel(MainRepositoryImpl mainRepository) {
        super(mainRepository);
        currentWeatherMutableLiveData = new MutableLiveData<>();
        forecastWeatherMutableLiveData = new MutableLiveData<>();
    }

    public void getCurrentWeather(String lat, String lon) {
        setIsLoading(true);
        getMainRepository().getApiRepository().getCurrentWeather(
                        lat,
                        lon,
                        ApiClient.API_KEY
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CurrentWeatherResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(CurrentWeatherResponse currentWeatherResponse) {
                        currentWeatherMutableLiveData.setValue(currentWeatherResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e);
                    }

                    @Override
                    public void onComplete() {
                        Timber.tag(TAG).d("onComplete");
                        setIsLoading(false);
                    }
                });

    }

    public void getCurrentWeatherByPlace(String q) {
        setIsLoading(true);
        getMainRepository().getApiRepository().getCurrentWeatherByPlace(q, ApiClient.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CurrentWeatherResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(CurrentWeatherResponse currentWeatherResponse) {
                        currentWeatherMutableLiveData.setValue(currentWeatherResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e);
                    }

                    @Override
                    public void onComplete() {
                        Timber.tag(TAG).d("onComplete");
                        setIsLoading(false);
                    }
                });
    }

    public void getForecastWeather(int id) {
        setIsLoading(true);
        getMainRepository().getApiRepository().getForecastWeather(id, ApiClient.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ForecastWeatherResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(ForecastWeatherResponse forecastWeatherResponse) {
                        forecastWeatherMutableLiveData.setValue(forecastWeatherResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.tag(TAG).e(e);
                    }

                    @Override
                    public void onComplete() {
                        Timber.tag(TAG).i("Fetching forecast completed...");
                    }
                });
    }

    public LiveData<ForecastWeatherResponse> getForecastWeatherAsLiveData() {
        return forecastWeatherMutableLiveData;
    }

    public LiveData<CurrentWeatherResponse> getCurrentWeatherAsLiveData() {
        return currentWeatherMutableLiveData;
    }

    public void setWeatherId(int id) {
        getMainRepository().getSharedPref().setId(id);
    }

    public int getWeatherId() {
        return getMainRepository().getSharedPref().getId();
    }

    public void deleteWeatherId() {
        getMainRepository().getSharedPref().clear("weather_id_key");
    }
}
