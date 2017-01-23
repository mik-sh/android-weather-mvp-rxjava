package com.miksh.weather.api;

import android.content.Context;

import com.miksh.weather.models.WeatherResponse;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;

/**
 * Created by mik.sh on 21/01/2017.
 */

public class RetrofitSingleton {

    private static volatile RetrofitSingleton INSTANCE;

    private ApiInterface apiService;

    private String BASE_URL = "http://api.openweathermap.org/";

    public static RetrofitSingleton getInstance() {

        RetrofitSingleton localInstance = INSTANCE;
        if (localInstance == null) {
            synchronized (RetrofitSingleton.class) {
                localInstance = INSTANCE;
                if (localInstance == null) {
                    INSTANCE = localInstance = new RetrofitSingleton();
                }
            }
        }
        return localInstance;
    }

    private RetrofitSingleton() {
        init();
    }

    private void init() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        apiService = retrofit.create(ApiInterface.class);
    }

    // Get weather
    private BehaviorSubject<WeatherResponse> weatherResponseBS;             // Subj for caching result in memory
    public Observable<WeatherResponse> getWeather(boolean forceReload) {    // forceReload = true for refresh cache

        if (weatherResponseBS == null || forceReload) {
            weatherResponseBS = BehaviorSubject.create();

            apiService
                    .getPoint("524901", 16, "30fcf35fee62d38e02fbfd7f33aa1acd", "metric")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext(response -> weatherResponseBS.onNext(response))
                    .doOnError(err -> weatherResponseBS.onNext(new WeatherResponse("400", err.getMessage())))
                    .onErrorResumeNext(err -> Observable.empty())
                    .subscribe();
        }

        return weatherResponseBS;
    }



}
