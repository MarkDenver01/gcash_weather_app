package com.denver.weather_gcash_app.presentation.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.denver.weather_gcash_app.data.response.current_weathers.CurrentWeatherResponse;
import com.denver.weather_gcash_app.domain.abstraction.ItemCallBack;
import com.denver.weather_gcash_app.presentation.base.BaseAdapter;
import com.denver.weather_gcash_app.presentation.base.BaseViewHolder;

import java.util.List;

public class CurrentWeatherAdapter extends BaseAdapter<CurrentWeatherResponse> {
    private ItemAdapterListener itemAdapterListener;

    public CurrentWeatherAdapter(List<CurrentWeatherResponse> weatherItems) {
        super(weatherItems);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return get() != null && !get().isEmpty() ? 1 : 0;
    }

    @Override
    public void add(List<CurrentWeatherResponse> weatherItems) {
        this.clear();
        super.add(weatherItems);
    }

    public void setItemAdapterListener(ItemAdapterListener itemAdapterListener) {
        this.itemAdapterListener = itemAdapterListener;
    }

    public interface ItemAdapterListener extends ItemCallBack<CurrentWeatherResponse> {
    }

   /* public class CurrentWeatherViewHolder extends BaseViewHolder implements ItemHolder.CurrentWeatherItemClickListener {
        private ItemListWeatherBinding itemListWeatherBinding;

        public CurrentWeatherViewHolder(@NonNull ItemListWeatherBinding itemListWeatherBinding) {
            super(itemListWeatherBinding.getRoot());
        }

        @Override
        public void onItemClick(View view, CurrentWeatherResponse item) {
            if (item != null) {
                itemAdapterListener.onItemClick(view, item);
            }
        }

        @Override
        public void onBind(int position) {
            final CurrentWeatherResponse currentWeatherModel = get().get(position);
        }
    }*/
}
