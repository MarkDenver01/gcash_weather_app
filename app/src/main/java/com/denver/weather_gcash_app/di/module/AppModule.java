package com.denver.weather_gcash_app.di.module;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import com.denver.weather_gcash_app.data.local.AppDatabase;
import com.denver.weather_gcash_app.data.repository.MainRepositoryImpl;
import com.denver.weather_gcash_app.data.repository.local.LocalDataRepositoryImpl;
import com.denver.weather_gcash_app.di.DatabaseInfo;
import com.denver.weather_gcash_app.domain.abstraction.LocalDataRepository;
import com.denver.weather_gcash_app.domain.abstraction.MainRepository;
import com.denver.weather_gcash_app.helper.Constants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = ViewModelModule.class)
public class AppModule {

    @Provides
    @Singleton
    public static Context provideApplicationContext(Application application) {
        return application;
    }

    @Provides
    @Singleton
    public static Retrofit providerRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl("")
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    public static Cache provideCache(Context context) {
        int cacheSize = 10 * 1024 * 1024;
        return new Cache(context.getCacheDir(), cacheSize);
    }

    @Provides
    @Singleton
    public static OkHttpClient provideOkHttpClient(Interceptor interceptor, Cache cache) {
        return new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(interceptor)
                .build();
    }

    @Provides
    @Singleton
    public static AppDatabase providerAppDatabase(Context context, @DatabaseInfo String databaseName) {
        return Room.databaseBuilder(context, AppDatabase.class, databaseName)
                .allowMainThreadQueries().build();
    }

    @Provides
    @DatabaseInfo
    public static String provideDatabaseName() {
        return Constants.DATABASE_NAME;
    }

    @Provides
    @Singleton
    public static LocalDataRepository provideLocalDataRepository(LocalDataRepositoryImpl localDataRepository) {
        return localDataRepository;
    }

    @Provides
    @Singleton
    public static MainRepository provideMainRepository(MainRepositoryImpl mainRepository) {
        return mainRepository;
    }
}