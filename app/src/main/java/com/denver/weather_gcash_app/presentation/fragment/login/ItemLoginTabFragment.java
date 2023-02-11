package com.denver.weather_gcash_app.presentation.fragment.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.denver.weather_gcash_app.R;
import com.denver.weather_gcash_app.databinding.ItemTabLoginBinding;
import com.denver.weather_gcash_app.presentation.base.BaseFragment;
import com.denver.weather_gcash_app.presentation.viewmodel.LoginViewModel;
import com.jakewharton.rxbinding3.view.RxView;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import kotlin.Unit;
import timber.log.Timber;

public class ItemLoginTabFragment extends BaseFragment<ItemTabLoginBinding, LoginViewModel> {
    private EditText mTxtUsername;
    private EditText mTxtPassword;
    private Button mBtnLogin;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (getArguments() != null) {
            getViewDataBinding().setLoginViewModel(getViewModel());
        }
        return getRootView();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initDesign(view);
        initButton();
    }

    @Override
    public LoginViewModel initViewModel() {
        return new ViewModelProvider(this, viewModelFactory).get(LoginViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_tab_login;
    }

    @Override
    public int getBindingVariables() {
        return com.denver.weather_gcash_app.BR.loginViewModel;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!mCompositeDisposable.isDisposed() || mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
    }

    private void initDesign(View view) {
        mTxtUsername = view.findViewById(R.id.edit_text_username);
        mTxtPassword = view.findViewById(R.id.edit_text_password);
        mBtnLogin = view.findViewById(R.id.button_login);
    }

    private void initButton() {
        mCompositeDisposable.add(RxView.clicks(mBtnLogin)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Unit>() {
                    @Override
                    public void accept(Unit unit) throws Exception {
                        Timber.tag("XXXX").e("CLICKABLE");
                        login(mTxtUsername.getText().toString(), mTxtPassword.getText().toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Timber.e(throwable);
                    }
                }));
    }

    private void login(String email, String password) {
        getViewModel().login(email, password);
    }
}
