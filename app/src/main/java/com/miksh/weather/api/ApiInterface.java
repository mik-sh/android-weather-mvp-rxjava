package com.miksh.weather.api;

import com.miksh.weather.models.WeatherResponse;


import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by mik.sh on 21/01/2017.
 */

public interface ApiInterface {

    @GET("data/2.5/forecast/daily?")
    Observable<WeatherResponse> getPoint(
            @Query("id") String cityId,
            @Query("cnt") int daysCount,
            @Query("appid") String apiKey,
            @Query("units") String units
    );

}
