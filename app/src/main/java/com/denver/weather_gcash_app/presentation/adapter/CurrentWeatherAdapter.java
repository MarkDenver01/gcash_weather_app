package com.denver.weather_gcash_app.presentation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.denver.weather_gcash_app.data.response.current_weathers.CurrentWeatherResponse;
import com.denver.weather_gcash_app.data.response.forecast_weathers.List;
import com.denver.weather_gcash_app.databinding.ItemListWeatherBinding;
import com.denver.weather_gcash_app.domain.abstraction.ItemCallBack;
import com.denver.weather_gcash_app.helper.Utils;
import com.denver.weather_gcash_app.presentation.base.BaseAdapter;
import com.denver.weather_gcash_app.presentation.base.BaseViewHolder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

import timber.log.Timber;

public class CurrentWeatherAdapter extends BaseAdapter<List> {
    private ItemAdapterListener itemAdapterListener;

    public CurrentWeatherAdapter(java.util.List<List> weatherItems) {
        super(weatherItems);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CurrentWeatherAdapter.CurrentWeatherViewHolder(
                ItemListWeatherBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        );
    }

    @Override
    public int getItemViewType(int position) {
        return get().size();
    }

    @Override
    public void add(java.util.List<List> weatherItems) {
        this.clear();
        super.add(weatherItems);
    }

    public void setItemAdapterListener(ItemAdapterListener itemAdapterListener) {
        this.itemAdapterListener = itemAdapterListener;
    }

    public interface ItemAdapterListener extends ItemCallBack<List> {
    }

    public class CurrentWeatherViewHolder extends BaseViewHolder implements ItemHolder.CurrentWeatherItemClickListener {
        private ItemListWeatherBinding itemListWeatherBinding;

        public CurrentWeatherViewHolder(@NonNull ItemListWeatherBinding itemListWeatherBinding) {
            super(itemListWeatherBinding.getRoot());
            this.itemListWeatherBinding = itemListWeatherBinding;
        }

        @Override
        public void onItemClick(View view, List item) {
            if (item != null) {
                itemAdapterListener.onItemClick(view, item);
            }
        }

        @Override
        public void onBind(int position) {
            final java.util.List<List> list = get();
            double celcius = Math.round(list.get(position).getMain().getTemp() - 273.15);
            String temp = String.format(Locale.getDefault(), "%.0f", celcius);
            double minCel = Math.round(list.get(position).getMain().getTempMin() - 273.15);
            String tempMin = String.format(Locale.getDefault(), "%.0f", minCel);
            double maxCel = Math.round(list.get(position).getMain().getTempMax() - 273.15);
            String tempMax = String.format(Locale.getDefault(), "%.0f", maxCel);

            String description = "";
            int weatherId = 0;
            if (list.get(position).getWeatherList().size() > 0) {
                description = "(" + list.get(position).getWeatherList().get(0).getMain() + ") "
                        + list.get(position).getWeatherList().get(0).getDescription();
                weatherId = list.get(position).getWeatherList().get(0).getId();
            }
            itemListWeatherBinding.cardView2.setBackgroundColor(itemView.getResources().getColor(Utils.getChangeColor(weatherId), null));
            itemListWeatherBinding.setItemHolder(new ItemHolder(list.get(position), this));
            itemListWeatherBinding.textDay.setText(Utils.setDateAndTimeFormat(list.get(position).getDtTxt()));
            itemListWeatherBinding.textTemp.setText("Temperature \n" + temp + "°C");
            itemListWeatherBinding.textMinMax.setText("Min/Max \n" + tempMin + "/" + tempMax + "°C");
            itemListWeatherBinding.textStatus.setText(description);
            itemListWeatherBinding.iconTemp.setAnimation(Utils.getWeatherStatus(weatherId));
            itemListWeatherBinding.iconTemp.playAnimation();
            itemListWeatherBinding.executePendingBindings();
        }
    }
}
