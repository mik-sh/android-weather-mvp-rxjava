package com.miksh.weather.map;

import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by mik.sh on 23/01/2017.
 */

public interface MapContract {

    interface View {

        void setPresenter(Presenter presenter);

        void addMarker(LatLng latLng);

        void cleanAllMarkers();

        void setCityToSubTitle(String cityName);

        void returnCoordinatesToWeatherList(LatLng latLng);
    }

    interface Presenter {

        void onMapClick(LatLng latLng);

        void getCityNameByLocation(Geocoder geocoder, LatLng latLng);

        void acceptMenuClick(LatLng latLng);

    }

}
