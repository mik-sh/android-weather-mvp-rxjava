package com.miksh.weather.map;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.miksh.weather.R;
import com.miksh.weather.WeatherApp;
import com.miksh.weather.api.RetrofitApi;
import com.miksh.weather.utils.NavigationUtils;

import javax.inject.Inject;

public class MapActivity extends AppCompatActivity {

    MapPresenter mapPresenter;

    @Inject
    RetrofitApi retrofitApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        WeatherApp.getRetrofitApiComponent().inject(this);

        MapFragment mapFragment =
                (MapFragment) getSupportFragmentManager().findFragmentById(R.id.map_container);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();

            NavigationUtils.addFragmentToActivity(
                    getSupportFragmentManager(),
                    mapFragment,
                    R.id.map_container
            );
        }

        mapPresenter = new MapPresenter(
                retrofitApi,
                mapFragment);

    }
}
