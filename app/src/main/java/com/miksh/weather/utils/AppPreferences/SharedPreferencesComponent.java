package com.miksh.weather.utils.AppPreferences;

import com.miksh.weather.ContextModule;
import com.miksh.weather.weather_list.WeatherListPresenter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by mik.sh on 27/01/2017.
 */

@Component(modules = {SharedPreferencesModule.class, ContextModule.class})
@Singleton
public interface SharedPreferencesComponent {

    void inject(WeatherListPresenter presenter);

}
