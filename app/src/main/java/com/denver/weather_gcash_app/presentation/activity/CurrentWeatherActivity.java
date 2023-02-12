package com.denver.weather_gcash_app.presentation.activity;

import static com.denver.weather_gcash_app.helper.Constants.PERMISSION_REQUEST_LOCATION;
import static com.denver.weather_gcash_app.helper.Utils.convertDblToStr;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.denver.weather_gcash_app.BR;
import com.denver.weather_gcash_app.R;
import com.denver.weather_gcash_app.data.response.current_weathers.CurrentWeatherResponse;
import com.denver.weather_gcash_app.data.response.forecast_weathers.ForecastWeatherResponse;
import com.denver.weather_gcash_app.data.response.forecast_weathers.List;
import com.denver.weather_gcash_app.databinding.ActivityCurrentWeatherBinding;
import com.denver.weather_gcash_app.domain.enums.AppStatus;
import com.denver.weather_gcash_app.helper.CustomDialogBuilder;
import com.denver.weather_gcash_app.helper.Utils;
import com.denver.weather_gcash_app.helper.provider.GeoLocationService;
import com.denver.weather_gcash_app.presentation.adapter.CurrentWeatherAdapter;
import com.denver.weather_gcash_app.presentation.base.BaseActivity;
import com.denver.weather_gcash_app.presentation.viewmodel.WeatherViewModel;
import com.jakewharton.rxbinding3.view.RxView;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import kotlin.Unit;
import timber.log.Timber;

public class CurrentWeatherActivity extends BaseActivity<ActivityCurrentWeatherBinding, WeatherViewModel>
        implements CurrentWeatherAdapter.ItemAdapterListener {
    @Inject
    Context context;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Inject
    CurrentWeatherAdapter currentWeatherAdapter;

    private Disposable disposable;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private GeoLocationService geoLocationService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDataBinding().buttonView.setEnabled(false);
        checkLocationPermission();
        currentWeatherAdapter.setItemAdapterListener(this);
        initButton();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!disposable.isDisposed() || disposable != null) {
            disposable.dispose();
        }

        if (!compositeDisposable.isDisposed() || compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_current_weather;
    }

    @Override
    public int getBindingVariables() {
        return BR.weatherViewModel;
    }

    @Override
    public WeatherViewModel initViewModel() {
        return new ViewModelProvider(this, viewModelFactory).get(WeatherViewModel.class);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_LOCATION) {
            Timber.tag("CURRENT_WEATHER").d("Normal session...");
            initGeoLocation();
            initResult();
        }
    }

    private void initGeoLocation() {
        geoLocationService = new GeoLocationService(CurrentWeatherActivity.this);
        if (geoLocationService.isCanGetLocation()) {
            getViewModel().getCurrentWeather(
                    convertDblToStr(geoLocationService.getLatitude()),
                    convertDblToStr(geoLocationService.getLongitude())
            );
        }
    }

    public void checkLocationPermission() {
        disposable = Observable.interval(1000, 1500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            Timber.tag("CURRENT_WEATHER").d("Requesting foreground location....");
                            locationPermissionDialog(true);
                            return;
                        }

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q // on Android 10, 11, 12
                                && checkSelfPermission(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                                != PackageManager.PERMISSION_GRANTED) {
                            Timber.tag("CURRENT_WEATHER").d("Requesting background location...");
                            locationPermissionDialog(false);
                            return;
                        }

                        initGeoLocation();
                        initResult();
                        disposable.dispose();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Timber.e(throwable);
                    }
                });
    }

    private void locationPermissionDialog(boolean isDenied) {
        Runnable upperEvent = new Runnable() {
            @Override
            public void run() {
                if (isDenied) {
                    requestForegroundOnly();
                } else {
                    requestWithBackground();
                }
            }
        };

        String message = isDenied ? "No permission allowed" : "Permission request warning";
        AppStatus appStatus = isDenied ? AppStatus.NO_PERMISSION_ALLOWED : AppStatus.LOCATION_REQUEST_WARNING;
        CustomDialogBuilder.oneButtonDialogBox(
                this,
                appStatus,
                message,
                upperEvent
        );
    }

    private void requestForegroundOnly() {
        requestPermissions(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION
        }, PERMISSION_REQUEST_LOCATION);
    }

    private void requestWithBackground() {
        requestPermissions(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION}, PERMISSION_REQUEST_LOCATION);
    }

    private void initResult() {
        getViewModel().getCurrentWeatherAsLiveData().observe(this, new Observer<CurrentWeatherResponse>() {
            @Override
            public void onChanged(CurrentWeatherResponse currentWeatherResponse) {
                double celcius = Math.round(currentWeatherResponse.getMain().getTemp() - 273.15);
                String temp = String.format(Locale.getDefault(), "%.0f", celcius);
                String wind = "Wind\n{spd: " + currentWeatherResponse.getWind().getSpeed() + "km/hr | deg: " + currentWeatherResponse.getWind().getDeg() + "% }";
                String humidity = "Humidity: " + currentWeatherResponse.getMain().getHumidity() + "%";
                String sunRiseSunSet = "sunrise: " + Utils.getTime(currentWeatherResponse.getSys().getSunrise()) + "\nsunset:" + Utils.getTime(currentWeatherResponse.getSys().getSunset());
                getDataBinding().textTemperature.setText(temp + "°C");
                getDataBinding().textCountry.setText(currentWeatherResponse.getSys().getCountry());
                getDataBinding().textName.setText(currentWeatherResponse.getName());
                getDataBinding().textHumidity.setText(humidity);
                getDataBinding().textRiseSet.setText(sunRiseSunSet);
                getDataBinding().textWindVelocity.setText(wind);
                getDataBinding().textDate.setText(Utils.getDate(currentWeatherResponse.getDt()));
                getDataBinding().textDescription.setText(currentWeatherResponse.getWeatherList().get(0).getMain());
                getDataBinding().iconTemp.setAnimation(Utils.getCurrentWeatherStatus(currentWeatherResponse.getWeatherList().get(0).getId(),
                        currentWeatherResponse.getSys().getSunset()));
                getDataBinding().iconTemp.playAnimation();

                // set weather id
                getViewModel().setWeatherId(currentWeatherResponse.getId());

                // call weather forecast
                getViewModel().getForecastWeather(currentWeatherResponse.getId());
            }
        });

        getViewModel().getForecastWeatherAsLiveData().observe(this, new Observer<ForecastWeatherResponse>() {
            @Override
            public void onChanged(ForecastWeatherResponse forecastWeatherResponse) {
                currentWeatherAdapter.add(forecastWeatherResponse.getList());
                displayRecyclerView();
                getDataBinding().buttonView.setEnabled(true);
            }
        });
    }

    private void displayRecyclerView() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false);
        getDataBinding().listWeather.setLayoutManager(layoutManager);
        getDataBinding().listWeather.setAdapter(currentWeatherAdapter);
    }

    @Override
    public void onItemClick(View view, List item) {

    }

    private void initButton() {
        compositeDisposable.add(RxView.clicks(getDataBinding().buttonView)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Unit>() {
                    @Override
                    public void accept(Unit unit) throws Exception {
                        Intent intent = new Intent(context, FetchWeatherActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Timber.e(throwable);
                    }
                }));
    }
}
