package com.miksh.weather.utils.AppPreferences;

import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by mik.sh on 27/01/2017.
 */

@Module
public class SharedPreferencesModule {

    @Provides
    @NonNull
    @Singleton
    public SharedPreferencesHelper provideSharedPreferencesHelper(Context context) {
        return new SharedPreferencesHelper(context);
    }

}
