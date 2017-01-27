package com.miksh.weather.api;

import com.miksh.weather.map.MapActivity;
import com.miksh.weather.weather_list.WeatherListActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by mik.sh on 27/01/2017.
 */

@Singleton
@Component(modules = {RetrofitApiModule.class})
public interface RetrofitApiComponent {

    void inject(WeatherListActivity activity);

    void inject(MapActivity activity);

}
