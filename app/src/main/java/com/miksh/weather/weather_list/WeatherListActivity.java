package com.miksh.weather.weather_list;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.miksh.weather.R;
import com.miksh.weather.api.RetrofitSingleton;
import com.miksh.weather.utils.NavigationUtils;

public class WeatherListActivity extends AppCompatActivity {

    WeatherListPresenter weatherListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_list);

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
                RetrofitSingleton.getInstance(),
                weatherListFragment);

    }
}
