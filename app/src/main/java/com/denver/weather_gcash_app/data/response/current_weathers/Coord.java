package com.denver.weather_gcash_app.data.response.current_weathers;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Coord implements Serializable {
    @SerializedName("lon")
    private double lon;
    @SerializedName("lat")
    private double lat;

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
}
