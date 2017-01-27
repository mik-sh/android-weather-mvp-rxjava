package com.miksh.weather;

import android.app.Application;

import com.miksh.weather.api.DaggerRetrofitApiComponent;
import com.miksh.weather.api.RetrofitApiComponent;
import com.miksh.weather.api.RetrofitApiModule;
import com.miksh.weather.utils.AppPreferences.DaggerSharedPreferencesComponent;
import com.miksh.weather.utils.AppPreferences.SharedPreferencesComponent;
import com.miksh.weather.utils.AppPreferences.SharedPreferencesModule;

/**
 * Created by mik.sh on 27/01/2017.
 */

public class WeatherApp extends Application {

    private static RetrofitApiComponent retrofitApiComponent;

    private static SharedPreferencesComponent sharedPreferencesComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        retrofitApiComponent = DaggerRetrofitApiComponent.builder()
                .retrofitApiModule(new RetrofitApiModule())
                .build();

        sharedPreferencesComponent = DaggerSharedPreferencesComponent.builder()
                .sharedPreferencesModule(new SharedPreferencesModule())
                .contextModule(new ContextModule(getApplicationContext()))
                .build();

    }

    public static RetrofitApiComponent getRetrofitApiComponent() {
        return retrofitApiComponent;
    }

    public static SharedPreferencesComponent getSharedPreferencesComponent() {
        return sharedPreferencesComponent;
    }

}
