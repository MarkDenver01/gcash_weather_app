package com.denver.weather_gcash_app.domain.model.current_weather;

import com.denver.weather_gcash_app.data.response.current_weathers.Wind;

public class CurrentWeatherModel {
    private String city;
    private String country;
    private Double currentTemperature;
    private String sunsetTime;
    private String sunriseTime;
    private int humidity;
    private Double speed;
    private String currentDate;
    private Double minTemperature;
    private Double maxTemperature;
    private String description;
    private int weatherId;

    public CurrentWeatherModel(String city, String country, Double currentTemperature, String sunsetTime, String sunriseTime, int humidity, Double speed, String currentDate, Double minTemperature, Double maxTemperature, String description, int weatherId) {
        this.city = city;
        this.country = country;
        this.currentTemperature = currentTemperature;
        this.sunsetTime = sunsetTime;
        this.sunriseTime = sunriseTime;
        this.humidity = humidity;
        this.speed = speed;
        this.currentDate = currentDate;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.description = description;
        this.weatherId = weatherId;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public Double getCurrentTemperature() {
        return currentTemperature;
    }

    public String getSunsetTime() {
        return sunsetTime;
    }

    public String getSunriseTime() {
        return sunriseTime;
    }

    public int getHumidity() {
        return humidity;
    }

    public Double getSpeed() {
        return speed;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public Double getMinTemperature() {
        return minTemperature;
    }

    public Double getMaxTemperature() {
        return maxTemperature;
    }

    public String getDescription() {
        return description;
    }

    public int getWeatherId() {
        return weatherId;
    }
}
