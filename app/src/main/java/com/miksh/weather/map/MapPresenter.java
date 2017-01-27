package com.miksh.weather.map;

import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.miksh.weather.api.RetrofitApi;

import java.io.IOException;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by mik.sh on 23/01/2017.
 */

public class MapPresenter
        implements MapContract.Presenter {

    @NonNull
    private RetrofitApi retrofitApi;

    @NonNull
    private MapContract.View mapView;

    MapPresenter(
            @NonNull RetrofitApi retrofitApi,
            @NonNull MapContract.View mapView) {

        this.retrofitApi = checkNotNull(retrofitApi, "Retrofit singleton cannot be null");
        this.mapView = checkNotNull(mapView, "View cannot be null");

        mapView.setPresenter(this);
    }


    @Override
    public void onMapClick(LatLng latLng) {
        mapView.cleanAllMarkers();
        mapView.addMarker(latLng);
    }

    @Override
    public void getCityNameByLocation(Geocoder geocoder, LatLng latLng) {
        try {
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            String cityName = addresses.get(0).getLocality();
            if (cityName == null) {
                cityName = addresses.get(0).getSubAdminArea();
            }
            if (cityName == null) {
                cityName = addresses.get(0).getAdminArea();
            }
            mapView.setCityToSubTitle(cityName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void acceptMenuClick(LatLng latLng) {
        if (latLng == null) {
            return;
        }
        mapView.returnCoordinatesToWeatherList(latLng);
    }
}
