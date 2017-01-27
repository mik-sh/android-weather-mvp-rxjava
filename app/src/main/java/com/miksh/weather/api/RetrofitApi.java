package com.miksh.weather.api;

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

public class RetrofitApi {

    private ApiInterface apiService;

    private final String BASE_URL = "http://api.openweathermap.org/";

    private final String API_KEY = "30fcf35fee62d38e02fbfd7f33aa1acd";


    public RetrofitApi() {
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

    private BehaviorSubject<WeatherResponse> weatherResponseBS;             // Subj for caching last weather result in memory

    public Observable<WeatherResponse> getWeather(boolean forceReload, String cityId) {    // forceReload = true for refresh cache

        if (weatherResponseBS == null || forceReload) {
            weatherResponseBS = BehaviorSubject.create();

            apiService
                    .getWeatherById(cityId, 16, API_KEY, "metric", "json")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext(response -> weatherResponseBS.onNext(response))
                    .doOnError(err -> weatherResponseBS.onNext(new WeatherResponse("400", err.getMessage())))
                    .onErrorResumeNext(err -> Observable.empty())
                    .subscribe();
        }

        return weatherResponseBS;
    }

    public Observable<WeatherResponse> getWeather(boolean forceReload, String lat, String lon) {    // forceReload = true for refresh cache

        if (weatherResponseBS == null || forceReload) {
            weatherResponseBS = BehaviorSubject.create();

            apiService
                    .getWeatherByLocation(lat, lon, 16, API_KEY, "metric", "json")
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
