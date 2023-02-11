package com.denver.weather_gcash_app.presentation.fragment.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.denver.weather_gcash_app.R;
import com.denver.weather_gcash_app.databinding.ItemTabLoginBinding;
import com.denver.weather_gcash_app.domain.enums.AppStatus;
import com.denver.weather_gcash_app.helper.CustomDialogBuilder;
import com.denver.weather_gcash_app.presentation.activity.CurrentWeatherActivity;
import com.denver.weather_gcash_app.presentation.base.BaseFragment;
import com.denver.weather_gcash_app.presentation.viewmodel.LoginViewModel;

import javax.inject.Inject;

public class ItemLoginTabFragment extends BaseFragment<ItemTabLoginBinding, LoginViewModel> {
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
    }

    private void initResult() {
        getViewDataBinding().getLoginViewModel().getLoginStatusAsLiveData().observe(getViewLifecycleOwner(), new Observer<AppStatus>() {
            @Override
            public void onChanged(AppStatus appStatus) {
                switch (appStatus) {
                    case LOGIN_SUCCESS:
                        Runnable runnable = new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(getActivity(), CurrentWeatherActivity.class);
                                startActivity(intent);
                                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            }
                        };
                        CustomDialogBuilder.oneButtonDialogBox(
                                getContext(),
                                AppStatus.SUCCESS,
                                "Success!!",
                                runnable
                        );
                        break;
                    case FILL_UP_ALL_FIELDS:
                        CustomDialogBuilder.oneButtonDialogBox(
                                getContext(),
                                AppStatus.FILL_UP_ALL_FIELDS,
                                "Some fields is/are empty!!",
                                null
                        );
                        break;
                    case LOGIN_FAILED:
                        CustomDialogBuilder.oneButtonDialogBox(
                                getContext(),
                                AppStatus.LOGIN_FAILED,
                                "Failed!!",
                                null
                        );
                        break;
                    case NOT_VALID_EMAIL:
                        CustomDialogBuilder.oneButtonDialogBox(
                                getContext(),
                                AppStatus.NOT_VALID_EMAIL,
                                "Invalid email!!",
                                null
                        );
                        break;
                    case ERROR:
                        break;
                }
            }
        });
    }
}
