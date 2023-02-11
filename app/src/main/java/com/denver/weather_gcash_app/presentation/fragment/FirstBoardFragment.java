package com.denver.weather_gcash_app.presentation.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieOnCompositionLoadedListener;
import com.denver.weather_gcash_app.R;
import com.denver.weather_gcash_app.presentation.activity.MainActivity;
import com.jakewharton.rxbinding3.view.RxView;

import java.util.concurrent.TimeUnit;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import kotlin.Unit;
import timber.log.Timber;

public class FirstBoardFragment extends Fragment {
    private LottieAnimationView mLottieCloudy;
    private Button mBtnNext;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_first_boarding, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mLottieCloudy = view.findViewById(R.id.imageView);
        mBtnNext = view.findViewById(R.id.button_next);
        mLottieCloudy.animate().translationX(0).setDuration(2000).setStartDelay(2000);
        initButton();

    }

    private void initButton() {
        mCompositeDisposable.add(RxView.clicks(mBtnNext)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Unit>() {
                    @Override
                    public void accept(Unit unit) throws Exception {
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Timber.e(throwable);
                    }
                }));
    }
}
