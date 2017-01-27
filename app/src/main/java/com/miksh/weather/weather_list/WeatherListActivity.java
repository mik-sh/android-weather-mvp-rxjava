package com.miksh.weather.weather_list;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.miksh.weather.R;
import com.miksh.weather.WeatherApp;
import com.miksh.weather.api.RetrofitApi;
import com.miksh.weather.utils.NavigationUtils;
import com.miksh.weather.utils.AppPreferences.SharedPreferencesHelper;

import javax.inject.Inject;

public class WeatherListActivity extends AppCompatActivity {

    WeatherListPresenter weatherListPresenter;

    @Inject
    RetrofitApi retrofitApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_list);

        WeatherApp.getRetrofitApiComponent().inject(this);

        WeatherListFragment weatherListFragment =
                (WeatherListFragment) getSupportFragmentManager().findFragmentById(R.id.weather_list_container);
        if (weatherListFragment == null) {
            weatherListFragment = WeatherListFragment.newInstance();

            NavigationUtils.addFragmentToActivity(
                    getSupportFragmentManager(),
                    weatherListFragment,
                    R.id.weather_list_container
            );
        }


        weatherListPresenter = new WeatherListPresenter(
                retrofitApi,
                weatherListFragment);

    }
}
