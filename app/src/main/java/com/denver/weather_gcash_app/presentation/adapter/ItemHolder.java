package com.denver.weather_gcash_app.presentation.adapter;


public class ItemHolder {
   /*  private final CurrentWeatherResponse currentWeatherModel;
    private final CurrentWeatherItemClickListener itemClickListener;
    public final ObservableField<String> currentDay;
    public ObservableField<Integer> currentWeatherIcon;
    public final ObservableField<String> currentTemperature;
    public final ObservableField<String> currentTempMin;
    public final ObservableField<String> currentTempMax;

    public ItemHolder(CurrentWeatherResponse currentWeatherModel, CurrentWeatherItemClickListener itemClickListener) {
       double celsius = Math.round(currentWeatherModel.getCurrentTemperature() - 275.15);
        double tempMin = Math.round(currentWeatherModel.getMinTemperature() - 275.15);
        double tempMax = Math.round(currentWeatherModel.getMaxTemperature() - 275.15);
        this.currentWeatherModel = currentWeatherModel;
        this.itemClickListener = itemClickListener;
        this.currentDay = new ObservableField<>(Utils.currentDay(currentWeatherModel.getCurrentDate()));
        this.currentTemperature = new ObservableField<>(String.format(Locale.getDefault(), "%.0f", celsius) + "°C");
        this.currentWeatherIcon = new ObservableField<>(Utils.getWeatherStatus(currentWeatherModel.getWeatherId()));
        this.currentTempMin = new ObservableField<>(String.format(Locale.getDefault(), "%.0f", tempMin) + "°");
        this.currentTempMax = new ObservableField<>(String.format(Locale.getDefault(), "%.0f", tempMax) + °");
    }

    public void onItemClick(View view) {
        itemClickListener.onItemClick(view, currentWeatherModel);
    }

    public interface CurrentWeatherItemClickListener extends ItemCallBack<CurrentWeatherModel> {

    }*/
}
