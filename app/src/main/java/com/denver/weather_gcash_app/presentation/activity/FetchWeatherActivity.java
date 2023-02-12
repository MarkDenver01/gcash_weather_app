package com.denver.weather_gcash_app.presentation.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.denver.weather_gcash_app.R;
import com.denver.weather_gcash_app.data.response.forecast_weathers.ForecastWeatherResponse;
import com.denver.weather_gcash_app.data.response.forecast_weathers.List;
import com.denver.weather_gcash_app.databinding.ActivityFetchWeatherBinding;
import com.denver.weather_gcash_app.presentation.adapter.CurrentWeatherAdapter;
import com.denver.weather_gcash_app.presentation.base.BaseActivity;
import com.denver.weather_gcash_app.presentation.viewmodel.WeatherViewModel;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class FetchWeatherActivity extends BaseActivity<ActivityFetchWeatherBinding, WeatherViewModel>
        implements CurrentWeatherAdapter.ItemAdapterListener {
    @Inject
    Context context;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Inject
    CurrentWeatherAdapter currentWeatherAdapter;

    private Disposable disposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentWeatherAdapter.setItemAdapterListener(this);
        fetchForecastWeather();
        initResult();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!disposable.isDisposed() || disposable != null) {
            disposable.dispose();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(context, CurrentWeatherActivity.class);
        getViewModel().deleteWeatherId();
        startActivity(intent);
        finish();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_fetch_weather;
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
    public void onItemClick(View view, List item) {

    }

    private void fetchForecastWeather() {
        disposable = Observable.timer(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        getViewModel().getForecastWeather(getViewModel().getWeatherId());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Timber.e(throwable);
                    }
                });
    }

    public void initResult() {
        getViewModel().getForecastWeatherAsLiveData().observe(this, new Observer<ForecastWeatherResponse>() {
            @Override
            public void onChanged(ForecastWeatherResponse forecastWeatherResponse) {
                currentWeatherAdapter.add(forecastWeatherResponse.getList());
                displayRecyclerView();
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

}
