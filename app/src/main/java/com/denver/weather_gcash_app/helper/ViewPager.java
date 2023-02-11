package com.denver.weather_gcash_app.helper;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.denver.weather_gcash_app.presentation.fragment.FirstBoardFragment;
import com.denver.weather_gcash_app.presentation.fragment.SecondBoardFragment;
import com.denver.weather_gcash_app.presentation.fragment.ThirdBoardFragment;

public class ViewPager extends FragmentStatePagerAdapter {
    private static final int PAGE_NO = 3;

    public ViewPager(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                FirstBoardFragment firstPage = new FirstBoardFragment();
                return firstPage;
            case 1:
                SecondBoardFragment secondPage = new SecondBoardFragment();
                return secondPage;
            case 2:
                ThirdBoardFragment thirdPage = new ThirdBoardFragment();
                return thirdPage;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return PAGE_NO;
    }
}
