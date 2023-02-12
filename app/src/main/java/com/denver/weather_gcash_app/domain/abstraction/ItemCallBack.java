package com.denver.weather_gcash_app.domain.abstraction;

import android.view.View;

public interface ItemCallBack<T> {
    void onItemClick(View view, T item);
}
