package com.miksh.weather.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mik.sh on 21/01/2017.
 */

public class City {

    private
    @SerializedName("name")
    String cityName;

    public String getCityName() {
        return cityName;
    }
}
