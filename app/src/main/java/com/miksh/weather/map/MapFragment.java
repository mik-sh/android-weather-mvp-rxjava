package com.miksh.weather.map;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.miksh.weather.R;

import static com.google.common.base.Preconditions.checkNotNull;

public class MapFragment extends SupportMapFragment
        implements OnMapReadyCallback, MapContract.View {

    private MapContract.Presenter mapPresenter;

    private GoogleMap googleMapInstance;

    private Geocoder geocoder;

    private LatLng currentLatLng;

    private final int MY_PERMISSIONS_REQUEST_LOCATION = 1;

    public MapFragment() {

    }

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        geocoder = new Geocoder(getContext());

        getMapAsync(this);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return super.onCreateView(layoutInflater, viewGroup, bundle);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.map_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.accept_coordinates:
                mapPresenter.acceptMenuClick(currentLatLng);
                break;
        }
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMapInstance = googleMap;
        configureMap();
    }

    private void configureMap() {

        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);

            return;
        }

        googleMapInstance.setMyLocationEnabled(true);

        googleMapInstance.setOnMapClickListener(latLng -> {
            mapPresenter.onMapClick(latLng);
            mapPresenter.getCityNameByLocation(geocoder, latLng);
        });
    }

    @Override
    public void setPresenter(@NonNull MapContract.Presenter presenter) {
        this.mapPresenter = checkNotNull(presenter);
    }

    @Override
    public void addMarker(LatLng latLng) {
        currentLatLng = latLng;
        googleMapInstance.addMarker(new MarkerOptions()
                .position(latLng)
                .draggable(false));
    }

    @Override
    public void cleanAllMarkers() {
        googleMapInstance.clear();
    }

    @Override
    public void setCityToSubTitle(String cityName) {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setSubtitle(cityName);
        }
    }

    @Override
    public void returnCoordinatesToWeatherList(LatLng latLng) {
        Intent coordinatesResult = new Intent();
        coordinatesResult.putExtra("CoordinatesResult", latLng);
        getActivity().setResult(Activity.RESULT_OK, coordinatesResult);
        getActivity().finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION:

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    configureMap();
                }
                break;
        }
    }
}
