package com.miksh.weather.models;

/**
 * Created by mik.sh on 23/01/2017.
 */

public class WeatherCardModel {

    private String dayOfWeek;

    private String date;

    private String mainTemperature;

    private String weatherDescription;

    private String morningTemperature;

    private String dayTemperature;

    private String eveningTemperature;

    private String nightTemperature;

    private String weatherImage;

    public WeatherCardModel(String dayOfWeek, String date, String mainTemperature,
                            String weatherDescription, String morningTemperature,
                            String dayTemperature, String eveningTemperature,
                            String nightTemperature, String weatherImage) {
        this.dayOfWeek = dayOfWeek;
        this.date = date;
        this.mainTemperature = mainTemperature;
        this.weatherDescription = weatherDescription;
        this.morningTemperature = morningTemperature;
        this.dayTemperature = dayTemperature;
        this.eveningTemperature = eveningTemperature;
        this.nightTemperature = nightTemperature;
        this.weatherImage = weatherImage;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public String getDate() {
        return date;
    }

    public String getMainTemperature() {
        return mainTemperature;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public String getMorningTemperature() {
        return morningTemperature;
    }

    public String getDayTemperature() {
        return dayTemperature;
    }

    public String getEveningTemperature() {
        return eveningTemperature;
    }

    public String getNightTemperature() {
        return nightTemperature;
    }

    public String getWeatherImage() {
        return weatherImage;
    }
}
