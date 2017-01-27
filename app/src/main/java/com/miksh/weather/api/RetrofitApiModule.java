package com.miksh.weather.api;

import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by mik.sh on 27/01/2017.
 */

@Module
public class RetrofitApiModule {

    @Provides
    @NonNull
    @Singleton
    public RetrofitApi provideRetrofitApi() {
        return new RetrofitApi();
    }

}
