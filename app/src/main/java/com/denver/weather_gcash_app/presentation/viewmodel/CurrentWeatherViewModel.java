package com.denver.weather_gcash_app.presentation.viewmodel;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.denver.weather_gcash_app.data.remote.client.ApiClient;
import com.denver.weather_gcash_app.data.repository.MainRepositoryImpl;
import com.denver.weather_gcash_app.data.response.current_weathers.CurrentWeatherResponse;
import com.denver.weather_gcash_app.domain.model.current_weather.CurrentWeatherModel;
import com.denver.weather_gcash_app.helper.Utils;
import com.denver.weather_gcash_app.presentation.base.MainViewModel;

import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class CurrentWeatherViewModel extends MainViewModel {
    private static final String TAG = "CURRENT_WEATHER";
    private MutableLiveData<CurrentWeatherModel> currentWeatherMutableLiveData;
    public String temperature;

    @Inject
    public CurrentWeatherViewModel(MainRepositoryImpl mainRepository) {
        super(mainRepository);
        currentWeatherMutableLiveData = new MutableLiveData<>();
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
                        double celsius = Math.round(currentWeatherResponse.getMain().getTemp() - 275.15);
                        String sunrise = Utils.getDateAndTime(currentWeatherResponse.getSys().getSunset());
                        String sunset = Utils.getDateAndTime(currentWeatherResponse.getSys().getSunrise());
                        Log.d("WEATHER", "weather info: "
                                + "\ntemp: " + celsius
                                + "\n sunrise: " + sunrise
                                + "\nsunset: " + sunset
                                + "\nlongitude: " + currentWeatherResponse.getCoord().getLon()
                                + "\nlatitude: " + currentWeatherResponse.getCoord().getLat());

                        currentWeatherMutableLiveData.postValue(new CurrentWeatherModel(
                                currentWeatherResponse.getName(),
                                currentWeatherResponse.getSys().getCountry(),
                                currentWeatherResponse.getMain().getTemp(),
                                Utils.getDateAndTime(currentWeatherResponse.getSys().getSunset()),
                                Utils.getDateAndTime(currentWeatherResponse.getSys().getSunrise()),
                                currentWeatherResponse.getMain().getHumidity(),
                                currentWeatherResponse.getWind().getSpeed(),
                                Utils.getDate(currentWeatherResponse.getDt()),
                                currentWeatherResponse.getMain().getTempMin(),
                                currentWeatherResponse.getMain().getTempMax(),
                                currentWeatherResponse.getWeatherList().get(0).getDescription(),
                                Integer.parseInt(currentWeatherResponse.getWeatherList().get(0).getId())
                        ));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e);
                    }

                    @Override
                    public void onComplete() {
                        Timber.tag(TAG).d("onComplete");
                    }
                });

    }

    public LiveData<CurrentWeatherModel> getCurrentWeatherAsLiveData() {
        return currentWeatherMutableLiveData;
    }
}
