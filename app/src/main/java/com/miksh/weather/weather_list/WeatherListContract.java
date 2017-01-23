package com.miksh.weather.weather_list;

import com.miksh.weather.models.WeatherCardModel;

import java.util.List;

/**
 * Created by mik.sh on 22/01/2017.
 */

public interface WeatherListContract {

    interface View {

        void setPresenter(Presenter presenter);

        void updateWeatherList(List<WeatherCardModel> weatherResponse);

        void updateCityTitle(String cityName);

        void setLoadIndicator(boolean show);

        void showError(String errTitle, String errMsg);

        void setEmptyResponseMessage(boolean show);

    }

    interface Presenter {

        void subscribe();

        void unsubscribe();

        void loadWeather(boolean forceReload);

    }

}
