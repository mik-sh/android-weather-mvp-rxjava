package com.miksh.weather.weather_list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.miksh.weather.R;
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

    @BindView(R.id.weather_recycler_view)
    RecyclerView weatherRecyclerView;

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
                weatherListPresenter.loadWeather(true);
                break;
            case R.id.choose_location:

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
        this.weatherResponse.clear();
        this.weatherResponse.addAll(weatherResponse);
        weatherAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateCityTitle(String cityName) {
        Log.d("", "");
    }

    @Override
    public void setLoadIndicator(boolean show) {
        Log.d("", "");
    }

    @Override
    public void showError(String errTitle, String errMsg) {
        Log.d("", "");
    }

    @Override
    public void showEmptyResponse() {
        Log.d("", "");
    }
}
