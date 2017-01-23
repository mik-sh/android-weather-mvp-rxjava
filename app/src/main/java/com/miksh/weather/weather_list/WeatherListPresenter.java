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

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
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

        weatherListView.setLoadIndicator(true);

        subscriptions.clear();
        Subscription subscription = retrofitSingleton
                .getWeather(forceReload)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(response -> {
                    if (response.getResultCode().equals("200")) {
                        processWeather(response);
                    } else {
                        String errorMsg = response.getErrorMessage().or("Error has occurred");
                        weatherListView.showError("Error", errorMsg);
                    }
                })
                .doOnError(err -> weatherListView.showError("Error", err.getLocalizedMessage()))
                .subscribe();

        subscriptions.add(subscription);
    }

    private void processWeather(@NonNull WeatherResponse weatherResponse) {
        weatherListView.setLoadIndicator(false);
        if (weatherResponse.isEmpty()) {
            weatherListView.showEmptyResponse();
        } else {
            List<WeatherCardModel> weatherCardsList = convertWeatherResponse(weatherResponse.getWeatherList());

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
