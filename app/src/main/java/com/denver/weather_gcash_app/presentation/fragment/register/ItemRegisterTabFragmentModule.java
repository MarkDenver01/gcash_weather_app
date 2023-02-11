package com.denver.weather_gcash_app.presentation.fragment.register;

import com.denver.weather_gcash_app.data.local.db.entity.LoginEntity;
import com.denver.weather_gcash_app.domain.model.LoginModel;

import dagger.Module;
import dagger.Provides;

@Module
public class ItemRegisterTabFragmentModule {

    @Provides
    public LoginModel registerLogin(String email, String password) {
        return new LoginModel(email, password);
    }

}
