package com.denver.weather_gcash_app.presentation.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import dagger.android.support.DaggerAppCompatActivity;

public abstract class BaseActivity<D extends ViewDataBinding, V extends BaseViewModel> extends DaggerAppCompatActivity {
    private D dataBinding;
    private V viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        dataBinding.setLifecycleOwner(this);
        viewModel = initViewModel();
    }

    public abstract int getLayoutId();

    public abstract V initViewModel();

    public V getViewModel() {
        return viewModel;
    }

    public D getDataBinding() {
        return dataBinding;
    }
}
