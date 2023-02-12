package com.denver.weather_gcash_app.presentation.adapter;


import android.view.View;
import com.denver.weather_gcash_app.data.response.forecast_weathers.List;
import com.denver.weather_gcash_app.domain.abstraction.ItemCallBack;

public class ItemHolder {
    private final List listWeatherItems;
    private final CurrentWeatherItemClickListener itemClickListener;

    public ItemHolder(List listWeatherItems, CurrentWeatherItemClickListener itemClickListener) {
        this.listWeatherItems = listWeatherItems;
        this.itemClickListener = itemClickListener;
    }

    public void onItemClick(View view) {
        itemClickListener.onItemClick(view, listWeatherItems);
    }

    public interface CurrentWeatherItemClickListener extends ItemCallBack<List> {

    }
}
