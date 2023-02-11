package com.denver.weather_gcash_app.presentation.activity;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.cuberto.liquid_swipe.LiquidPager;
import com.denver.weather_gcash_app.R;
import com.denver.weather_gcash_app.helper.ViewPager;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class InitialActivity extends AppCompatActivity {
    private LiquidPager mLiquidPager;
    private ViewPager mViewPager;
    private LottieAnimationView mLottieAnimationView;

    private Disposable mDisposableTimer = new CompositeDisposable();
    private Disposable mLogoDisposableTimer = new CompositeDisposable();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDesign();
        initTimer();
    }

    @Override
    protected void onDestroy() {
        mDisposableTimer.dispose();
        mLogoDisposableTimer.dispose();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
    }

    private void initDesign() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        mLiquidPager = findViewById(R.id.pager);
        mLottieAnimationView = findViewById(R.id.lottieAnimationView);
        mViewPager = new ViewPager(getSupportFragmentManager(), 1);
    }

    private void initTimer() {
        mLogoDisposableTimer = Observable.interval(1000, 2000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        mLottieAnimationView.animate().translationX(0).setDuration(2000).setStartDelay(4000);
                        mLogoDisposableTimer.dispose();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Timber.e(throwable.getLocalizedMessage());
                    }
                });

        mDisposableTimer = Observable.interval(5000, 6000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        mLiquidPager.setAdapter(mViewPager);
                        mDisposableTimer.dispose();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Timber.e(throwable.getLocalizedMessage());
                    }
                });
    }
}
