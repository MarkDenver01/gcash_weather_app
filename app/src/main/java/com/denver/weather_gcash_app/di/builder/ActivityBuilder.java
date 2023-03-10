package com.denver.weather_gcash_app.di.builder;

import com.denver.weather_gcash_app.presentation.activity.CurrentWeatherActivity;
import com.denver.weather_gcash_app.presentation.activity.FetchWeatherActivity;
import com.denver.weather_gcash_app.presentation.activity.MainActivity;
import com.denver.weather_gcash_app.presentation.activity.InitialActivity;
import com.denver.weather_gcash_app.presentation.fragment.login.ItemLoginTabFragmentProvider;
import com.denver.weather_gcash_app.presentation.fragment.register.ItemRegisterTabFragmentProvider;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = {
            ItemLoginTabFragmentProvider.class,
            ItemRegisterTabFragmentProvider.class,
    })
    public abstract MainActivity contributeMainActivity();

    @ContributesAndroidInjector
    public abstract InitialActivity contributeInitialActivity();

    @ContributesAndroidInjector
    public abstract CurrentWeatherActivity contributeCurrentWeatherActivity();

    @ContributesAndroidInjector
    public abstract FetchWeatherActivity contributeFetchWeatherActivity();
}
