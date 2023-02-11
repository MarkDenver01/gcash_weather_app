package com.denver.weather_gcash_app.presentation.fragment.register;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ItemRegisterTabFragmentProvider {
    @ContributesAndroidInjector(modules = ItemRegisterTabFragmentModule.class)
    public abstract ItemRegisterTabFragment provideRegisterLoginFragment();

}
