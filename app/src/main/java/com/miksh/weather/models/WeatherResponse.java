package com.miksh.weather.models;

import com.google.common.base.Optional;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mik.sh on 21/01/2017.
 */

public class WeatherResponse {

    private
    @SerializedName("cod")
    String resultCode;

    private
    @SerializedName("errorMsg")
    String errorMessage;

    private
    @SerializedName("city")
    City city;

    private
    @SerializedName("list")
    List<WeatherItem> weatherList;

    public WeatherResponse(String code, String error) {
        this.resultCode = code;
        this.errorMessage = error;
    }

    public String getResultCode() {
        return resultCode;
    }

    public City getCity() {
        return city;
    }

    public List<WeatherItem> getWeatherList() {
        return weatherList;
    }

    public Optional<String> getErrorMessage() {
        return Optional.fromNullable(errorMessage);
    }

    public boolean isEmpty() {
        return weatherList == null || weatherList.isEmpty();
    }
}
