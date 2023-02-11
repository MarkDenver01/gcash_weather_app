package com.denver.weather_gcash_app.presentation.fragment.login;

import com.denver.weather_gcash_app.domain.model.LoginModel;

import dagger.Module;
import dagger.Provides;

@Module
public class ItemLoginTabFragmentModule {
    @Provides
    public LoginModel login(String email, String password) {
        return new LoginModel(email, password);
    }
}
