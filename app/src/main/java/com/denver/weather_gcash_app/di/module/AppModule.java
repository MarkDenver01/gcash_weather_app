package com.denver.weather_gcash_app.di.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.room.Room;

import com.denver.weather_gcash_app.R;
import com.denver.weather_gcash_app.data.local.AppDatabase;
import com.denver.weather_gcash_app.data.local.preference.PreferenceManager;
import com.denver.weather_gcash_app.data.remote.client.ApiClient;
import com.denver.weather_gcash_app.data.remote.client.ApiService;
import com.denver.weather_gcash_app.data.repository.MainRepositoryImpl;
import com.denver.weather_gcash_app.data.repository.local.LocalDataRepositoryImpl;
import com.denver.weather_gcash_app.data.repository.remote.RemoteDataRepositoryImpl;
import com.denver.weather_gcash_app.di.DatabaseInfo;
import com.denver.weather_gcash_app.domain.abstraction.LocalDataRepository;
import com.denver.weather_gcash_app.domain.abstraction.MainRepository;
import com.denver.weather_gcash_app.domain.abstraction.RemoteDataRepository;
import com.denver.weather_gcash_app.domain.abstraction.SharedPrefsData;
import com.denver.weather_gcash_app.helper.Constants;
import com.denver.weather_gcash_app.helper.Utils;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
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
    public static ApiService provideApiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }

    @Provides
    @Singleton
    public static Retrofit providerRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(ApiClient.BASE_URL)
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
    public static Interceptor provideInterceptor(Context context) {
        return chain -> {
            Request request = chain.request();
            if (!Utils.isNetworkAvailable(context)) {
                int maxStale = 60 * 60 * 24 * 28;
                request = request.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale" + maxStale)
                        .build();
            } else {
                int maxAge = 5;
                request = request.newBuilder()
                        .header("Cache-Control", "public, max-age = " + maxAge)
                        .build();
            }
            return chain.proceed(request);
        };
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
    public static RemoteDataRepository provideRemoteDataRepository(RemoteDataRepositoryImpl remoteDataRepository) {
        return remoteDataRepository;
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

    @Provides
    @Singleton
    public static SharedPrefsData provideSharedData(PreferenceManager preferenceManager) {
        return preferenceManager;
    }

    @Provides
    @Singleton
    public static SharedPreferences provideSharedPreferences(Context context) {
        return context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
    }
}
