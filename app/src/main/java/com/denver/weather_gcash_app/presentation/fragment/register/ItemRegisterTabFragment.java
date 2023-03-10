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
import com.denver.weather_gcash_app.domain.enums.AppStatus;
import com.denver.weather_gcash_app.helper.CustomDialogBuilder;
import com.denver.weather_gcash_app.presentation.base.BaseFragment;
import com.denver.weather_gcash_app.presentation.viewmodel.LoginViewModel;

import javax.inject.Inject;

import timber.log.Timber;

public class ItemRegisterTabFragment extends BaseFragment<ItemTabRegistrationBinding, LoginViewModel> {

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
        initResult();
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
        getViewDataBinding().getLoginViewModel().getLoginStatusAsLiveData().observe(getViewLifecycleOwner(), new Observer<AppStatus>() {
            @Override
            public void onChanged(AppStatus appStatus) {
                switch (appStatus) {
                    case SUCCESS:
                        CustomDialogBuilder.oneButtonDialogBox(
                                getContext(),
                                AppStatus.SUCCESS,
                                "Success!!",
                                null
                        );
                        break;
                    case ALREADY_EXIST:
                        Runnable runnable = new Runnable() {
                            @Override
                            public void run() {
                                Timber.e("Already exist!!!!");
                            }
                        };
                        CustomDialogBuilder.oneButtonDialogBox(
                                getContext(),
                                AppStatus.ALREADY_EXIST,
                                "Account already\n exist!!",
                                null
                        );
                        break;
                    case NOT_VALID_EMAIL:
                        CustomDialogBuilder.oneButtonDialogBox(
                                getContext(),
                                AppStatus.NOT_VALID_EMAIL,
                                "Email not valid!",
                                null
                        );
                        break;
                    case FILL_UP_ALL_FIELDS:
                        CustomDialogBuilder.oneButtonDialogBox(
                                getContext(),
                                AppStatus.FILL_UP_ALL_FIELDS,
                                "Some fields\n is/are empty!",
                                null
                        );
                        break;
                    case PASSWORD_NOT_MATCH:
                        CustomDialogBuilder.oneButtonDialogBox(
                                getContext(),
                                AppStatus.PASSWORD_NOT_MATCH,
                                "Password does\n not match!",
                                null
                        );
                        break;
                    case ERROR:
                        CustomDialogBuilder.oneButtonDialogBox(
                                getContext(),
                                AppStatus.ERROR,
                                "Unknown error!",
                                null
                        );
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
