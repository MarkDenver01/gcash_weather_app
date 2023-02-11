package com.denver.weather_gcash_app.presentation.fragment.login;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ItemLoginTabFragmentProvider {

    @ContributesAndroidInjector(modules = ItemLoginTabFragmentModule.class)
    public abstract ItemLoginTabFragment provideItemLoginFragment();
}
