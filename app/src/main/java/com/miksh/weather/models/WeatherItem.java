package com.miksh.weather.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mik.sh on 21/01/2017.
 */

public class WeatherItem implements Serializable {

    private
    @SerializedName("dt")
    long timeStamp;

    private
    @SerializedName("temp")
    TemperatureParam temperatureParams;

    private
    @SerializedName("weather")
    List<WeatherParam> weatherParams;

    public long getTimeStamp() {
        return timeStamp;
    }

    public TemperatureParam getTemperatureParams() {
        return temperatureParams;
    }

    public List<WeatherParam> getWeatherParams() {
        return weatherParams;
    }
}
