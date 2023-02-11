package com.denver.weather_gcash_app.data.local.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.denver.weather_gcash_app.data.local.db.entity.LoginEntity;

import io.reactivex.Observable;
import io.reactivex.Single;

@Dao
public interface LoginDao {
    @Insert
    void insertLogin(LoginEntity loginEntity);

    @Query("SELECT * FROM login_table WHERE email = :email AND password = :password")
    LiveData<LoginEntity> login(String email, String password);

    @Query("SELECT * FROM login_table WHERE email = :email")
    boolean isAccountExist(String email);
}
