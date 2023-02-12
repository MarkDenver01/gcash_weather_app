package com.denver.weather_gcash_app.data.response.forecast_weathers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Sys implements Serializable {
    @Expose
    @SerializedName("pod")
    private String pod;

    public String getPod() {
        return pod;
    }
}
