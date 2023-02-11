package com.denver.weather_gcash_app.presentation.fragment.login;

import com.denver.weather_gcash_app.data.local.db.entity.LoginEntity;

import dagger.Module;
import dagger.Provides;

@Module
public class ItemLoginTabFragmentModule {
    @Provides
    LoginEntity login(String email, String password) {
        return new LoginEntity(email, password);
    }
}
