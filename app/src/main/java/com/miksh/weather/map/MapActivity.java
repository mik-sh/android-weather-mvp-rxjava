package com.miksh.weather.map;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.miksh.weather.R;
import com.miksh.weather.api.RetrofitSingleton;
import com.miksh.weather.utils.NavigationUtils;

public class MapActivity extends AppCompatActivity {

    MapPresenter mapPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

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
                RetrofitSingleton.getInstance(),
                mapFragment);

    }
}
