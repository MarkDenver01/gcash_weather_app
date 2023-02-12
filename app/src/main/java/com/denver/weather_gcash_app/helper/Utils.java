package com.denver.weather_gcash_app.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.format.DateFormat;

import com.denver.weather_gcash_app.R;

import java.util.Calendar;
import java.util.Locale;

public class Utils {
    public static boolean isStringNullOrEmpty(final String str) {
        return (str == null || str.isEmpty());
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public static String convertDblToStr(Double val) {
        return String.valueOf(val);
    }

    public static String getDateAndTime(long time) {
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.setTimeInMillis(time * 1000);
        String date = DateFormat.format("yyyy-MM-dd hh:mm:ss a", cal).toString();
        return date;
    }

    public static String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.setTimeInMillis(time * 1000);
        String date = DateFormat.format("MMMM dd, yyyy", cal).toString();
        return date;
    }

    public static String currentDay(long time) {
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.setTimeInMillis(time * 1000);
        String date = DateFormat.format("EEEE", cal).toString();
        return date;
    }

    public static int getWeatherStatus(int id) {
        if (id / 100 == 2) {
            return R.raw.storm_weather;
        } else if (id / 100 == 3) {
            return R.raw.rainy_weather;
        } else if (id / 100 == 5) {
            return R.raw.rainy_weather;
        } else if (id / 100 == 6) {
            return R.raw.snow_weather;
        } else if (id / 100 == 8) {
            return R.raw.cloudy_weather;
        } else if (id == 800) {
            return R.raw.clear_day;
        } else if (id == 801) {
            return R.raw.few_clouds;
        } else if (id == 803) {
            return R.raw.broken_clouds;
        } else {
            return R.raw.unknown;
        }
    }
}
