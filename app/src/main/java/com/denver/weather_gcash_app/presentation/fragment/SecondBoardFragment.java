package com.denver.weather_gcash_app.presentation.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.denver.weather_gcash_app.R;

public class SecondBoardFragment extends Fragment {
    private LottieAnimationView mLottieSunny;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_second_boarding, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mLottieSunny = view.findViewById(R.id.imageView);
        mLottieSunny.animate().translationX(0).setDuration(2000).setStartDelay(2000);
    }
}
