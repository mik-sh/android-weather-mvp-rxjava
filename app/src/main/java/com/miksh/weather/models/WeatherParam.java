package com.miksh.weather.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by mik.sh on 21/01/2017.
 */

public class WeatherParam implements Serializable {

    private
    @SerializedName("icon")
    String icon;

    private
    @SerializedName("main")
    String weatherTitle;

    private
    @SerializedName("description")
    String weatherDescription;

    public String getWeatherIcon() {
        return icon;
    }

    public String getWeatherTitle() {
        return weatherTitle;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }
}
