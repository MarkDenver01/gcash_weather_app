package com.denver.weather_gcash_app.presentation.activity;

import static com.denver.weather_gcash_app.helper.Constants.PERMISSION_REQUEST_LOCATION;
import static com.denver.weather_gcash_app.helper.Utils.convertDblToStr;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.airbnb.lottie.LottieAnimationView;
import com.denver.weather_gcash_app.MainApplication;
import com.denver.weather_gcash_app.R;
import com.denver.weather_gcash_app.databinding.ActivityCurrentWeatherBinding;
import com.denver.weather_gcash_app.domain.enums.AppStatus;
import com.denver.weather_gcash_app.domain.model.current_weather.CurrentWeatherModel;
import com.denver.weather_gcash_app.helper.Constants;
import com.denver.weather_gcash_app.helper.CustomDialogBuilder;
import com.denver.weather_gcash_app.helper.Utils;
import com.denver.weather_gcash_app.helper.provider.GeoLocationService;
import com.denver.weather_gcash_app.presentation.base.BaseActivity;
import com.denver.weather_gcash_app.presentation.viewmodel.CurrentWeatherViewModel;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class CurrentWeatherActivity extends BaseActivity<ActivityCurrentWeatherBinding, CurrentWeatherViewModel> {
    @Inject
    Context context;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private TextView txtTemperature;
    private TextView txtCountry;
    private TextView txtCity;
    private TextView txtWind;
    private TextView txtHumidity;
    private TextView txtDate;
    private TextView txtDescription;
    private LottieAnimationView lottieAnimationView;

    private Disposable disposable;
    private GeoLocationService geoLocationService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDesign();
        checkLocationPermission();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!disposable.isDisposed() || disposable != null) {
            disposable.dispose();
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_current_weather;
    }

    @Override
    public CurrentWeatherViewModel initViewModel() {
        return new ViewModelProvider(this, viewModelFactory).get(CurrentWeatherViewModel.class);
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

    private void initDesign() {
        txtTemperature = findViewById(R.id.text_temperature);
        txtCountry = findViewById(R.id.text_country);
        txtCity = findViewById(R.id.text_name);
        txtHumidity = findViewById(R.id.text_humidity);
        txtWind = findViewById(R.id.text_wind_velocity);
        txtDate = findViewById(R.id.text_date);
        txtDescription = findViewById(R.id.text_description);
        lottieAnimationView = findViewById(R.id.icon_temp);
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
        getViewModel().getCurrentWeatherAsLiveData().observe(this, new Observer<CurrentWeatherModel>() {
            @Override
            public void onChanged(CurrentWeatherModel currentWeatherModel) {
                double celsius = Math.round(currentWeatherModel.getCurrentTemperature() - 275.15);
                txtTemperature.setText(String.format(Locale.getDefault(), "%.0f", celsius) + "°C");
                txtCountry.setText(currentWeatherModel.getCountry());
                txtCity.setText(currentWeatherModel.getCity());
                txtHumidity.setText("Humidity: " + currentWeatherModel.getHumidity() + "%");
                txtWind.setText("Wind (Speed): " + currentWeatherModel.getSpeed() + "km/hr");
                txtDate.setText(currentWeatherModel.getCurrentDate());
                txtDescription.setText(currentWeatherModel.getDescription());
                lottieAnimationView.setAnimation(getWeatherStatus(currentWeatherModel.getWeatherId()));
                lottieAnimationView.playAnimation();
            }
        });
    }

    private int getWeatherStatus(int id) {
        if (id / 100 == 2) {
            return R.raw.storm_weather;
        } else if (id / 100 == 3) {
            return R.raw.rainy_weather;
        } else if (id / 100 == 5) {
            return R.raw.rainy_weather;
        } else if (id / 100 == 6) {
            return R.raw.snow_weather;
        } else if (id / 100 == 8) {
            return R.raw.cloudy_weather;
        } else if (id == 800) {
            return R.raw.clear_day;
        } else if (id == 801) {
            return R.raw.few_clouds;
        } else if (id == 803) {
            return R.raw.broken_clouds;
        } else {
            return R.raw.unknown;
        }
    }
}
