package com.denver.weather_gcash_app.data.response.forecast_weathers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Weather implements Serializable {
    @Expose
    @SerializedName("id")
    private int id;
    @Expose
    @SerializedName("main")
    private String main;
    @Expose
    @SerializedName("description")
    private String description;
    @Expose
    @SerializedName("icon")
    private String icon;

    public int getId() {
        return id;
    }

    public String getMain() {
        return main;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }
}
