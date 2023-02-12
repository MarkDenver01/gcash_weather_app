package com.denver.weather_gcash_app.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.format.DateFormat;

import com.denver.weather_gcash_app.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

import timber.log.Timber;

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

    public static String getTime(long time) {
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.setTimeInMillis(time * 1000);
        String date = DateFormat.format("hh:mm a", cal).toString();
        return date;
    }

    public static String setDateAndTimeFormat(String dateTime) {
        SimpleDateFormat getDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat newDateFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm aa");
        Date date = null;
        try {
            date = getDateFormat.parse(dateTime);
        } catch (ParseException e) {
            Timber.e(e);
        }
        return newDateFormat.format(date);
    }

    public static String getCurrentDate() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(cal.getTime());
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

    public static int getChangeColor(int id) {
        if (id / 100 == 2) {
            return R.color.storm_weather;
        } else if (id / 100 == 3) {
            return R.color.rainy_weather;
        } else if (id / 100 == 5) {
            return R.color.alert_border;
        } else if (id / 100 == 6) {
            return R.color.snow_weather;
        } else if (id / 100 == 8) {
            return R.color.cloudy_weather;
        } else if (id == 800) {
            return R.color.clear_day;
        } else if (id == 801) {
            return R.color.few_clouds;
        } else if (id == 803) {
            return R.color.broken_clouds;
        } else if (id == 802) {
            return R.color.few_clouds;
        } else if (id == 500) {
            return R.color.rainy_weather;
        } else if (id == 804) {
            return R.color.overcast_cloud;
        } else {
            return R.color.unknown;
        }
    }
}
