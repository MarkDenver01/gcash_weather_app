package com.denver.weather_gcash_app.presentation.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.denver.weather_gcash_app.presentation.fragment.login.ItemLoginTabFragment;
import com.denver.weather_gcash_app.presentation.fragment.register.ItemRegisterTabFragment;

public class MainAdapter extends FragmentPagerAdapter {
    private Context context;
    private int mTotalTabs = 0;

    public MainAdapter(FragmentManager fm, Context context, int totalTabs) {
        super(fm, totalTabs);
        this.context = context;
        this.mTotalTabs = totalTabs;
    }

    @Override
    public int getCount() {
        return mTotalTabs;
    }

    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ItemLoginTabFragment itemLoginTabFragment = new ItemLoginTabFragment();
                return itemLoginTabFragment;
            case 1:
                ItemRegisterTabFragment itemRegisterTabFragment = new ItemRegisterTabFragment();
                return itemRegisterTabFragment;
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Login";
            case 1:
                return "Register";
            default:
                return null;
        }
    }
}
