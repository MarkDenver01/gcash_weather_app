package com.denver.weather_gcash_app.data.response.forecast_weathers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Wind implements Serializable {
    @Expose
    @SerializedName("speed")
    private double speed;
    @Expose
    @SerializedName("deg")
    private double deg;
    @Expose
    @SerializedName("gust")
    private double gust;

    public double getSpeed() {
        return speed;
    }

    public double getDeg() {
        return deg;
    }

    public double getGust() {
        return gust;
    }
}
