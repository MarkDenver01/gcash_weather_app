package com.denver.weather_gcash_app.presentation.base;

import androidx.lifecycle.MutableLiveData;

import com.denver.weather_gcash_app.data.local.db.entity.LoginEntity;
import com.denver.weather_gcash_app.data.repository.MainRepositoryImpl;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class MainViewModel extends BaseViewModel {
    private MutableLiveData<LoginEntity> loginEntityMutableLiveData;
    private CompositeDisposable compositeDisposable;

    public MainViewModel(MainRepositoryImpl mainRepository) {
        super(mainRepository);
        loginEntityMutableLiveData = new MutableLiveData<>();
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (!compositeDisposable.isDisposed() || compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }

    public void addDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    public MutableLiveData<LoginEntity> getLoginAsLiveData() {
        return loginEntityMutableLiveData;
    }
}
