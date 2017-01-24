package com.miksh.weather.weather_list;

import android.support.annotation.NonNull;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.miksh.weather.api.RetrofitSingleton;
import com.miksh.weather.models.TemperatureParam;
import com.miksh.weather.models.WeatherCardModel;
import com.miksh.weather.models.WeatherItem;
import com.miksh.weather.models.WeatherParam;
import com.miksh.weather.models.WeatherResponse;
import com.miksh.weather.utils.SharedPreferencesHelper;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by mik.sh on 22/01/2017.
 */

public class WeatherListPresenter implements WeatherListContract.Presenter {

    @NonNull
    private RetrofitSingleton retrofitSingleton;

    @NonNull
    private WeatherListContract.View weatherListView;

    @NonNull
    private CompositeSubscription subscriptions;

    private boolean isLoading = false;

    WeatherListPresenter(
            @NonNull RetrofitSingleton retrofitSingleton,
            @NonNull WeatherListContract.View weatherListView) {

        this.retrofitSingleton = checkNotNull(retrofitSingleton, "Retrofit singleton cannot be null");
        this.weatherListView = checkNotNull(weatherListView, "View cannot be null");

        subscriptions = new CompositeSubscription();
        weatherListView.setPresenter(this);
    }

    public void subscribe() {
        loadWeather(false);
    }

    @Override
    public void unsubscribe() {
        subscriptions.clear();
    }

    @Override
    public void loadWeather(boolean forceReload) {

        long lastQueryId = SharedPreferencesHelper.getInstance()
                .getLong(SharedPreferencesHelper.Key.LAST_CITY_ID_LONG);


        if (lastQueryId == 0) {
            lastQueryId = 519690;   // Saint-Petersburg id
        }

        startLoading();

        subscriptions.clear();
        Subscription subscription = retrofitSingleton
                .getWeather(forceReload, String.valueOf(lastQueryId))
                .doOnNext(this::weatherSuccessResponse)
                .doOnError(this::weatherErrorResponse)
                .subscribe();

        subscriptions.add(subscription);
    }

    @Override
    public void loadWeather(boolean forceReload, String lat, String lon) {

        startLoading();

        subscriptions.clear();
        Subscription subscription = retrofitSingleton
                .getWeather(forceReload, lat, lon)
                .doOnNext(this::weatherSuccessResponse)
                .doOnError(this::weatherErrorResponse)
                .subscribe();

        subscriptions.add(subscription);

    }

    private void startLoading() {

        setLoading(true);

        weatherListView.setLoadIndicator(true);
        weatherListView.setEmptyResponseMessage(false);
    }

    private void weatherSuccessResponse(WeatherResponse response) {

        setLoading(false);

        if (response.getResultCode().equals("200")) {
            processWeather(response);
        } else {
            String errorMsg = response.getErrorMessage().or("Error has occurred");
            weatherListView.showError("Error", errorMsg);
        }
    }

    private void weatherErrorResponse(Throwable err) {

        setLoading(false);

        weatherListView.showError("Error", err.getLocalizedMessage());
    }

    @Override
    public boolean isLoading() {
        return isLoading;
    }

    @Override
    public void openMap() {
        weatherListView.showMapUI();
    }

    private void setLoading(boolean isLoading) {
        this.isLoading = isLoading;
    }

    private void processWeather(@NonNull WeatherResponse weatherResponse) {
        SharedPreferencesHelper.getInstance()
                .put(SharedPreferencesHelper.Key.LAST_CITY_ID_LONG, weatherResponse.getCity().getCityId());

        weatherListView.setLoadIndicator(false);
        if (weatherResponse.isEmpty()) {
            weatherListView.setEmptyResponseMessage(true);
        } else {
            List<WeatherCardModel> weatherCardsList = convertWeatherResponse(weatherResponse.getWeatherList());

            weatherListView.setEmptyResponseMessage(false);
            weatherListView.updateCityTitle(weatherResponse.getCity().getCityName());
            weatherListView.updateWeatherList(weatherCardsList);
        }
    }

    private List<WeatherCardModel> convertWeatherResponse(List<WeatherItem> weatherResponseList) {
        return Stream.of(weatherResponseList)
                .map(respondItem -> {

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(respondItem.getTimeStamp() * 1000);

                    TemperatureParam temperature = respondItem.getTemperatureParams();
                    WeatherParam weather = respondItem.getWeatherParams().get(0);

                    Locale locale = new Locale("en", "US");


                    String dayOfWeek = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, locale);

                    String date = calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, locale).toUpperCase()
                            .concat(" ")
                            .concat(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));

                    String mainTemperature = String.valueOf(Math.round(temperature.getDayTemperature()));

                    String weatherDescription = Character.toUpperCase(weather.getWeatherDescription().charAt(0)) +
                            weather.getWeatherDescription().substring(1);

                    String morningTemperature = String.valueOf(Math.round(temperature.getMorningTemperature()));

                    String dayTemperature = String.valueOf(Math.round(temperature.getDayTemperature()));

                    String eveningTemperature = String.valueOf(Math.round(temperature.getEveningTemperature()));

                    String nightTemperature = String.valueOf(Math.round(temperature.getNightTemperature()));

                    String weatherImage = weather.getWeatherIcon();

                    return new WeatherCardModel(dayOfWeek, date, mainTemperature,
                            weatherDescription, morningTemperature, dayTemperature,
                            eveningTemperature, nightTemperature, weatherImage);
                })
                .collect(Collectors.toList());
    }


}
