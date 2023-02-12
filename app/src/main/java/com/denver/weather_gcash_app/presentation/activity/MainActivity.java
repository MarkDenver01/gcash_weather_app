package com.denver.weather_gcash_app.presentation.activity;

import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.denver.weather_gcash_app.R;
import com.denver.weather_gcash_app.databinding.ActivityMainBinding;
import com.denver.weather_gcash_app.presentation.adapter.MainAdapter;
import com.denver.weather_gcash_app.presentation.base.BaseActivity;
import com.denver.weather_gcash_app.presentation.base.MainViewModel;
import com.google.android.material.tabs.TabLayout;

import javax.inject.Inject;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDesign();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public int getBindingVariables() {
        return 0;
    }

    @Override
    public MainViewModel initViewModel() {
        return new ViewModelProvider(this, viewModelFactory).get(MainViewModel.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
    }

    private void initDesign() {
        mTabLayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.view_pager);
        mTabLayout.addTab(mTabLayout.newTab().setText("Login"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Register"));
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final MainAdapter mainAdapter = new MainAdapter(
                getSupportFragmentManager(),
                this.getApplicationContext(),
                this.mTabLayout.getTabCount());
        mViewPager.setAdapter(mainAdapter);
        //mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mTabLayout.setScrollPosition(position, 0f, true);
            }

            @Override
            public void onPageSelected(int position) {
                mViewPager.setCurrentItem(position, false);
                mTabLayout.getTabAt(position).select();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mTabLayout.setupWithViewPager(this.mViewPager);
    }
}