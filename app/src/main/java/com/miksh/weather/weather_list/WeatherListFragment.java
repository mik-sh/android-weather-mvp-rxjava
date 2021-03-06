package com.miksh.weather.weather_list;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.miksh.weather.R;
import com.miksh.weather.map.MapActivity;
import com.miksh.weather.models.WeatherCardModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.google.common.base.Preconditions.checkNotNull;


public class WeatherListFragment extends Fragment
        implements WeatherListContract.View {

    private WeatherListContract.Presenter weatherListPresenter;

    private List<WeatherCardModel> weatherResponse = new ArrayList<>();

    private WeatherListAdapter weatherAdapter;

    private static final String ERROR_DIALOG_TAG = "ErrorDialog";

    private final int COORDINATES_REQUEST = 2;

    @BindView(R.id.weather_recycler_view)
    RecyclerView weatherRecyclerView;

    @BindView(R.id.weather_progress_bar)
    ProgressBar weatherProgressBar;

    @BindView(R.id.empty_list_message)
    TextView emptyListMessage;

    public WeatherListFragment() {

    }

    public static WeatherListFragment newInstance() {
        return new WeatherListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        weatherListPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        weatherListPresenter.unsubscribe();
    }

    @Override
    public void setPresenter(@NonNull WeatherListContract.Presenter presenter) {
        this.weatherListPresenter = checkNotNull(presenter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_weather_list, container, false);
        ButterKnife.bind(this, rootView);

        setHasOptionsMenu(true);

        recyclerViewSetUp();

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.weather_list_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh_list:
                if (!weatherListPresenter.isLoading()) {
                    weatherResponse.clear();
                    weatherAdapter.notifyDataSetChanged();
                    weatherListPresenter.loadWeather(true);
                }
                break;
            case R.id.choose_location:
                    weatherListPresenter.openMap();
                break;
        }
        return true;
    }

    private void recyclerViewSetUp() {
        weatherRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        weatherRecyclerView.setLayoutManager(layoutManager);
        weatherAdapter = new WeatherListAdapter(weatherResponse, getContext());
        weatherRecyclerView.setAdapter(weatherAdapter);
    }

    @Override
    public void updateWeatherList(List<WeatherCardModel> weatherResponse) {
        weatherProgressBar.setVisibility(View.GONE);
        this.weatherResponse.clear();
        this.weatherResponse.addAll(weatherResponse);
        weatherRecyclerView.smoothScrollToPosition(0);
        weatherAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateCityTitle(String cityName) {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setSubtitle("In ".concat(cityName));
        }
    }

    @Override
    public void setLoadIndicator(boolean show) {
        if (show) {
            weatherProgressBar.setVisibility(View.VISIBLE);
        } else {
            weatherProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showError(String errTitle, String errMsg) {
        setEmptyResponseMessage(true);
        setLoadIndicator(false);

        FragmentManager fragmentManager = getFragmentManager();
        ErrorDialogFragment errorDialogFragment = ErrorDialogFragment.newInstance();
        errorDialogFragment.show(fragmentManager, ERROR_DIALOG_TAG);
    }

    @Override
    public void setEmptyResponseMessage(boolean show) {
        if (show) {
            emptyListMessage.setVisibility(View.VISIBLE);
        } else {
            emptyListMessage.setVisibility(View.GONE);
        }
    }

    @Override
    public void showMapUI() {
        Intent intent = new Intent(getContext(), MapActivity.class);
        startActivityForResult(intent, COORDINATES_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == COORDINATES_REQUEST) {

            if (resultCode == Activity.RESULT_OK) {
                LatLng latLng = data.getParcelableExtra("CoordinatesResult");
                String lat = String.valueOf(latLng.latitude);
                String lon = String.valueOf(latLng.longitude);
                weatherListPresenter.loadWeather(true, lat, lon);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
