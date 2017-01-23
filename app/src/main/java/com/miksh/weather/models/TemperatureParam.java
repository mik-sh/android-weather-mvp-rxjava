package com.miksh.weather.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by mik.sh on 21/01/2017.
 */

public class TemperatureParam implements Serializable {

    private
    @SerializedName("morn")
    float morningTemperature;

    private
    @SerializedName("day")
    float dayTemperature;

    private
    @SerializedName("eve")
    float eveningTemperature;

    private
    @SerializedName("night")
    float nightTemperature;

    public float getMorningTemperature() {
        return morningTemperature;
    }

    public float getEveningTemperature() {
        return eveningTemperature;
    }

    public float getDayTemperature() {
        return dayTemperature;
    }

    public float getNightTemperature() {
        return nightTemperature;
    }
}
