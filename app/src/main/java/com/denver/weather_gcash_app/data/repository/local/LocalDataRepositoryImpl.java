package com.denver.weather_gcash_app.data.repository.local;

import androidx.lifecycle.LiveData;

import com.denver.weather_gcash_app.data.local.AppDatabase;
import com.denver.weather_gcash_app.data.local.db.entity.LoginEntity;
import com.denver.weather_gcash_app.domain.abstraction.LocalDataRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

@Singleton
public class LocalDataRepositoryImpl implements LocalDataRepository {
    private AppDatabase appDatabase;

    @Inject
    public LocalDataRepositoryImpl(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }


    @Override
    public void insertLogin(LoginEntity loginEntity) {
        appDatabase.getLoginDao().insertLogin(loginEntity);
    }

    @Override
    public LiveData<LoginEntity> login(String email, String password) {
        return appDatabase.getLoginDao().login(email, password);
    }

    @Override
    public boolean isAccountExisting(String email) {
        return appDatabase.getLoginDao().isAccountExist(email);
    }


}
