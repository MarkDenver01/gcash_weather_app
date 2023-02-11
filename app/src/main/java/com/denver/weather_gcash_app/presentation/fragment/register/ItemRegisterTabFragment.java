package com.denver.weather_gcash_app.presentation.fragment.register;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.denver.weather_gcash_app.R;
import com.denver.weather_gcash_app.databinding.ItemTabRegistrationBinding;
import com.denver.weather_gcash_app.domain.enums.LoginStatus;
import com.denver.weather_gcash_app.presentation.base.BaseFragment;
import com.denver.weather_gcash_app.presentation.viewmodel.LoginViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.jakewharton.rxbinding3.view.RxView;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import kotlin.Unit;
import timber.log.Timber;

public class ItemRegisterTabFragment extends BaseFragment<ItemTabRegistrationBinding, LoginViewModel> {
    private MaterialAlertDialogBuilder materialAlertDialogBuilder;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

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
    }

    @Override
    public int getBindingVariables() {
        return com.denver.weather_gcash_app.BR.loginViewModel;
    }

    @Override
    public LoginViewModel initViewModel() {
        return new ViewModelProvider(this, viewModelFactory).get(LoginViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_tab_registration;
    }

    private void initResult() {
        getViewDataBinding().getLoginViewModel().getLoginStatusAsLiveData().observe(this, new Observer<LoginStatus>() {
            @Override
            public void onChanged(LoginStatus loginStatus) {
                switch (loginStatus) {
                    case SUCCESS:
                        break;
                    case ALREADY_EXIST:
                        break;
                    case NOT_VALID_EMAIL:
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
