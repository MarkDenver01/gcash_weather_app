package com.denver.weather_gcash_app.data.local.preference;

import android.content.Context;
import android.content.SharedPreferences;

import com.denver.weather_gcash_app.domain.abstraction.SharedPrefsData;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PreferenceManager implements SharedPrefsData {

    private SharedPreferences sharedPreferences;

    @Inject
    public PreferenceManager(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public void setIsLoggedIn(boolean bool) {
        sharedPreferences.edit().putBoolean("logged_in_key", bool).commit();
    }

    @Override
    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean("logged_in_key", false);
    }

    @Override
    public void setId(int id) {
        sharedPreferences.edit().putInt("weather_id_key", id).commit();
    }

    @Override
    public int getId() {
        return sharedPreferences.getInt("weather_id_key", 0);
    }

    @Override
    public void clear(String key) {
        sharedPreferences.edit().remove(key).commit();
    }
}
