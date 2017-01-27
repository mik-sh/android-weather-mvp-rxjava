package com.miksh.weather;

import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by mik.sh on 27/01/2017.
 */

@Module
public class ContextModule {

    private Context appContext;

    public ContextModule(@NonNull Context context) {
        this.appContext = context;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return appContext;
    }

}
