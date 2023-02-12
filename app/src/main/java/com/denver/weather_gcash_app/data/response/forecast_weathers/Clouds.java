package com.denver.weather_gcash_app.data.response.forecast_weathers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Clouds implements Serializable {
    @Expose
    @SerializedName("all")
    private int all;
}
