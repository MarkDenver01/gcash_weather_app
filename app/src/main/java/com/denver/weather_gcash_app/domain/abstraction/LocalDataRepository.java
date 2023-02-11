package com.denver.weather_gcash_app.domain.abstraction;

import androidx.lifecycle.LiveData;

import com.denver.weather_gcash_app.data.local.db.entity.LoginEntity;
import com.denver.weather_gcash_app.domain.model.LoginModel;

import io.reactivex.Observable;

public interface LocalDataRepository {
    void insertLogin(LoginEntity loginEntity);

    LiveData<LoginEntity> login(String email, String password);

    boolean isAccountExisting(String email);
}
