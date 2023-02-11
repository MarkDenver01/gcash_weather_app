package com.denver.weather_gcash_app.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.denver.weather_gcash_app.data.local.dao.LoginDao;
import com.denver.weather_gcash_app.data.entity.LoginEntity;

@Database(entities = {LoginEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract LoginDao getLoginDao();
}
