package com.denver.weather_gcash_app.data.response.forecast_weathers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Rain implements Serializable {
    @Expose
    @SerializedName("3h")
    private Double _3h;

    public Double get_3h() {
        return _3h;
    }
}
