package com.denver.weather_gcash_app.data.repository;

import com.denver.weather_gcash_app.data.repository.local.LocalDataRepositoryImpl;
import com.denver.weather_gcash_app.data.repository.remote.RemoteDataRepositoryImpl;
import com.denver.weather_gcash_app.domain.abstraction.MainRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MainRepositoryImpl implements MainRepository {
    private LocalDataRepositoryImpl db;
    private RemoteDataRepositoryImpl api;

    @Inject
    public MainRepositoryImpl(RemoteDataRepositoryImpl api, LocalDataRepositoryImpl db) {
        this.db = db;
        this.api = api;
    }

    public RemoteDataRepositoryImpl getApiRepository() {
        return api;
    }

    public LocalDataRepositoryImpl getDbRepository() {
        return db;
    }
}
