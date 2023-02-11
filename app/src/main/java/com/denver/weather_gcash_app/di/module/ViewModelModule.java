package com.denver.weather_gcash_app.di.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.denver.weather_gcash_app.ViewModelProviderFactory;
import com.denver.weather_gcash_app.di.ViewModelKey;
import com.denver.weather_gcash_app.presentation.viewmodel.CurrentWeatherViewModel;
import com.denver.weather_gcash_app.presentation.viewmodel.LoginViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    public abstract ViewModel bindLoginViewModel(LoginViewModel loginViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(CurrentWeatherViewModel.class)
    public abstract ViewModel bindCurrentWeatherViewModel(CurrentWeatherViewModel currentWeatherViewModel);

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelProviderFactory viewModelProviderFactory);
}
