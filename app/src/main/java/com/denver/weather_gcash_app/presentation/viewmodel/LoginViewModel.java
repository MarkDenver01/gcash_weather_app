package com.denver.weather_gcash_app.presentation.viewmodel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.denver.weather_gcash_app.data.entity.LoginEntity;
import com.denver.weather_gcash_app.data.repository.MainRepositoryImpl;
import com.denver.weather_gcash_app.domain.enums.AppStatus;
import com.denver.weather_gcash_app.domain.model.login.LoginModel;
import com.denver.weather_gcash_app.helper.Utils;
import com.denver.weather_gcash_app.presentation.base.MainViewModel;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;


public class LoginViewModel extends MainViewModel {
    private static final String TAG = "LOG_IN";
    private Disposable disposable = new CompositeDisposable();
    // register
    public MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();
    public MutableLiveData<String> confirmPassword = new MutableLiveData<>();
    //login
    public MutableLiveData<String> loginEmail = new MutableLiveData<>();
    public MutableLiveData<String> loginPassword = new MutableLiveData<>();

    private LoginModel loginModel;

    private MutableLiveData<AppStatus> loginStatusMutableLiveData;

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

    public void onLoginClicked() {
        disposable = Observable.timer(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        loginModel = new LoginModel(loginEmail.getValue(), loginPassword.getValue());
                        Timber.tag("xxxxxx").e("xxxx e: " + loginModel.getEmail() + " p : " + loginModel.getPassword());
                        if (!loginModel.isEmailValid()) {
                            Timber.tag(TAG).i("Invalid email address");
                            getMainRepository().getSharedPref().setIsLoggedIn(false);
                            loginStatusMutableLiveData.setValue(AppStatus.NOT_VALID_EMAIL);
                            return;
                        }

                        if (isEmpty()) {
                            Timber.tag(TAG).i("Invalid email address");
                            getMainRepository().getSharedPref().setIsLoggedIn(false);
                            loginStatusMutableLiveData.setValue(AppStatus.FILL_UP_ALL_FIELDS);
                            return;
                        }

                        if (!getMainRepository().getDbRepository().isAccountExisting(loginModel.getEmail())) {
                            Timber.tag(TAG).i("Login failed");
                            getMainRepository().getSharedPref().setIsLoggedIn(false);
                            loginStatusMutableLiveData.setValue(AppStatus.LOGIN_FAILED);
                            return;
                        }

                        getMainRepository().getDbRepository()
                                .login(loginModel.getEmail(), loginModel.getPassword());
                        getMainRepository().getSharedPref().setIsLoggedIn(true);
                        Timber.tag(TAG).i("Success!");
                        loginStatusMutableLiveData.setValue(AppStatus.LOGIN_SUCCESS);
                        clearFields(true);
                        disposable.dispose();


                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Timber.e(throwable);
                        getMainRepository().getSharedPref().setIsLoggedIn(false);
                        loginStatusMutableLiveData.setValue(AppStatus.ERROR);
                    }
                });
    }

    public void onRegisterClicked() {
        disposable = Observable.timer(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        loginModel = new LoginModel(email.getValue(), password.getValue());
                        if (!loginModel.isEmailValid()) {
                            Timber.tag(TAG).i("Invalid email address");
                            getMainRepository().getSharedPref().setIsLoggedIn(false);
                            loginStatusMutableLiveData.setValue(AppStatus.NOT_VALID_EMAIL);
                            return;
                        }

                        if (getMainRepository().getDbRepository().isAccountExisting(loginModel.getEmail())) {
                            Timber.tag(TAG).i("Account already exist");
                            getMainRepository().getSharedPref().setIsLoggedIn(false);
                            loginStatusMutableLiveData.setValue(AppStatus.ALREADY_EXIST);
                            return;
                        }

                        if (isPasswordNotNull()
                                && !password.getValue().equals(confirmPassword.getValue())) {
                            Timber.tag(TAG).i("Password not match");
                            getMainRepository().getSharedPref().setIsLoggedIn(false);
                            loginStatusMutableLiveData.setValue(AppStatus.PASSWORD_NOT_MATCH);
                            return;
                        }

                        if (isSomeFieldsNull()) {
                            Timber.tag(TAG).i("Some fields is/are null");
                            getMainRepository().getSharedPref().setIsLoggedIn(false);
                            loginStatusMutableLiveData.setValue(AppStatus.FILL_UP_ALL_FIELDS);
                            return;
                        }

                        getMainRepository().getDbRepository()
                                .insertLogin(new LoginEntity(loginModel.getEmail(), loginModel.getPassword()));
                        Timber.tag(TAG).i("Account created!");
                        getMainRepository().getSharedPref().setIsLoggedIn(false);
                        loginStatusMutableLiveData.setValue(AppStatus.SUCCESS);
                        clearFields(false);
                        disposable.dispose();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Timber.e(throwable);
                        getMainRepository().getSharedPref().setIsLoggedIn(false);
                        loginStatusMutableLiveData.setValue(AppStatus.ERROR);
                    }
                });
    }

    public boolean isPasswordNotNull() {
        return !Utils.isStringNullOrEmpty(confirmPassword.getValue())
                && !Utils.isStringNullOrEmpty(password.getValue());
    }

    public boolean isSomeFieldsNull() {
        return (Utils.isStringNullOrEmpty(email.getValue())
                || Utils.isStringNullOrEmpty(password.getValue())
                || Utils.isStringNullOrEmpty(confirmPassword.getValue()));
    }

    public boolean isEmpty() {
        return (Utils.isStringNullOrEmpty(loginEmail.getValue())
                || Utils.isStringNullOrEmpty(loginPassword.getValue()));
    }

    public void clearFields(boolean isLogin) {
        loginModel.clear();
        if (isLogin) {
            loginEmail.setValue("");
            loginPassword.setValue("");
        } else {
            email.setValue("");
            password.setValue("");
            confirmPassword.setValue("");
        }
    }

    public boolean isAlreadyLoggedIn() {
        return getMainRepository().getSharedPref().isLoggedIn();
    }

    public LiveData<AppStatus> getLoginStatusAsLiveData() {
        return loginStatusMutableLiveData;
    }
}
