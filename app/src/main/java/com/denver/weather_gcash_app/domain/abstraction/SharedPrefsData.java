package com.denver.weather_gcash_app.domain.abstraction;

public interface SharedPrefsData {
    void setIsLoggedIn(boolean bool);
    boolean isLoggedIn();

    void setId(int id);

    int getId();

    void  clear(String key);
}
