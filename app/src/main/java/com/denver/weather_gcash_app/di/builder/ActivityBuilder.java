package com.denver.weather_gcash_app.di.builder;

import com.denver.weather_gcash_app.presentation.activity.MainActivity;
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
}
