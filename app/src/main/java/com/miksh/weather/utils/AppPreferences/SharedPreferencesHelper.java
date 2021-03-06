package com.miksh.weather.utils.AppPreferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by mik.sh on 24/01/2017.
 */
public class SharedPreferencesHelper {

    private SharedPreferences sharedPreferences;

    private static final String SETTINGS_NAME = "weather_settings";

    public enum Key {

        LAST_CITY_ID_LONG

    }

    public SharedPreferencesHelper(Context appContext) {
        init(appContext);
    }

    private void init(Context appContext) {
        sharedPreferences = appContext.getSharedPreferences(SETTINGS_NAME, Context.MODE_PRIVATE);
    }



    public void put(Key key, long val) {
        sharedPreferences.edit()
                .putLong(key.name(), val)
                .apply();
    }

    public void put(Key key, float val) {
        sharedPreferences.edit()
                .putFloat(key.name(), val)
                .apply();
    }

    public void put(Key key, String val) {
        sharedPreferences.edit()
                .putString(key.name(), val)
                .apply();
    }

    public String getString(Key key) {
        return sharedPreferences.getString(key.name(), null);
    }

    public int getInt(Key key) {
        return sharedPreferences.getInt(key.name(), 0);
    }

    public long getLong(Key key) {
        return sharedPreferences.getLong(key.name(), 0);
    }

    public float getFloat(Key key) {
        return sharedPreferences.getFloat(key.name(), 0);
    }

}
