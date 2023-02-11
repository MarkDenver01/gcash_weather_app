package com.denver.weather_gcash_app.presentation.viewmodel;


import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.denver.weather_gcash_app.data.local.db.entity.LoginEntity;
import com.denver.weather_gcash_app.data.repository.MainRepositoryImpl;
import com.denver.weather_gcash_app.domain.enums.LoginStatus;
import com.denver.weather_gcash_app.domain.model.LoginModel;
import com.denver.weather_gcash_app.presentation.base.MainViewModel;
import com.jakewharton.rxbinding3.view.RxView;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;


public class LoginViewModel extends MainViewModel {
    private Disposable disposable = new CompositeDisposable();
    public MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();

    private MutableLiveData<LoginStatus> loginStatusMutableLiveData;

    @Inject
    public LoginViewModel(MainRepositoryImpl mainRepository) {
        super(mainRepository);
        loginStatusMutableLiveData = new MutableLiveData<>();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (disposable.isDisposed() || disposable != null) {
            disposable.dispose();
        }
    }

    public void login(String email, String password) {
    }

    public void onRegisterClicked() {
        disposable = Observable.timer(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        LoginModel loginModel = new LoginModel(email.getValue(), password.getValue());
                        if (!loginModel.isEmailValid()) {
                            loginStatusMutableLiveData.setValue(LoginStatus.NOT_VALID_EMAIL);
                            return;
                        }

                        if (getMainRepository().getDbRepository().isAccountExisting(loginModel.getEmail())) {
                            loginStatusMutableLiveData.setValue(LoginStatus.ALREADY_EXIST);
                            return;
                        }

                        getMainRepository().getDbRepository()
                                .insertLogin(new LoginEntity(loginModel.getEmail(), loginModel.getPassword()));
                        loginStatusMutableLiveData.setValue(LoginStatus.SUCCESS);
                        disposable.dispose();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Timber.e(throwable);
                        loginStatusMutableLiveData.setValue(LoginStatus.ERROR);
                    }
                });
    }

    public LiveData<LoginStatus> getLoginStatusAsLiveData() {
        return loginStatusMutableLiveData;
    }
}
