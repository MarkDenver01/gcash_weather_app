package com.denver.weather_gcash_app.presentation.base;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.denver.weather_gcash_app.data.repository.MainRepositoryImpl;

public abstract class BaseViewModel extends ViewModel {
    private MainRepositoryImpl mainRepository;
    private MutableLiveData<Boolean> isLoading;

    public BaseViewModel(MainRepositoryImpl mainRepository) {
        this.mainRepository = mainRepository;
        isLoading = new MutableLiveData<>();
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void setIsLoading(boolean isLoading) {
        this.isLoading.setValue(isLoading);
    }

    public MainRepositoryImpl getMainRepository() {
        return mainRepository;
    }
}
