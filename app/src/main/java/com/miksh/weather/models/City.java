package com.miksh.weather.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mik.sh on 21/01/2017.
 */

public class City {

    private
    @SerializedName("id")
    long cityId;

    private
    @SerializedName("name")
    String cityName;

    private
    @SerializedName("lon")
    String longitude;

    private
    @SerializedName("lat")
    String latitude;

    public String getCityName() {
        return cityName;
    }

    public long getCityId() {
        return cityId;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }
}
