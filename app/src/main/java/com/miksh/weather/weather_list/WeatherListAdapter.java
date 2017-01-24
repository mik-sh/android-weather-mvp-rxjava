package com.miksh.weather.weather_list;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.miksh.weather.R;
import com.miksh.weather.models.WeatherCardModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mik.sh on 23/01/2017.
 */

public class WeatherListAdapter extends RecyclerView.Adapter<WeatherListAdapter.ViewHolder> {

    private List<WeatherCardModel> weatherDataSet;

    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.day_of_week)
        TextView dayOfWeek;

        @BindView(R.id.date)
        TextView date;

        @BindView(R.id.main_temperature)
        TextView mainTemperature;

        @BindView(R.id.weather_description)
        TextView weatherDescription;

        @BindView(R.id.morning_temperature)
        TextView morningTemperature;

        @BindView(R.id.day_temperature)
        TextView dayTemperature;

        @BindView(R.id.evening_temperature)
        TextView eveningTemperature;

        @BindView(R.id.night_temperature)
        TextView nightTemperature;

        @BindView(R.id.weather_image)
        ImageView weatherImage;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public WeatherListAdapter(List<WeatherCardModel> weatherDataSet, Context context) {
        this.weatherDataSet = weatherDataSet;
        this.context = context;
    }

    @Override
    public WeatherListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View rootView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_weather_list, parent, false);

        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(WeatherListAdapter.ViewHolder holder, int position) {

        WeatherCardModel weatherItem = weatherDataSet.get(position);

        String celsius = context.getString(R.string.celsius);

        String imageName = "icon_".concat((weatherItem.getWeatherImage().replace(".png", "")).replace("n", "d"));
        final int imageId = context.getResources()
                .getIdentifier(imageName, "drawable", context.getPackageName());

        Drawable weatherImage = ContextCompat.getDrawable(context, imageId);

        holder.dayOfWeek.setText(weatherItem.getDayOfWeek());
        holder.date.setText(weatherItem.getDate());
        holder.mainTemperature.setText(String.format(celsius, weatherItem.getDayTemperature()));
        holder.weatherDescription.setText(weatherItem.getWeatherDescription());
        holder.morningTemperature.setText(String.format(celsius, weatherItem.getMorningTemperature()));
        holder.dayTemperature.setText(String.format(celsius, weatherItem.getDayTemperature()));
        holder.eveningTemperature.setText(String.format(celsius, weatherItem.getEveningTemperature()));
        holder.nightTemperature.setText(String.format(celsius, weatherItem.getNightTemperature()));
        holder.weatherImage.setImageDrawable(weatherImage);

    }

    @Override
    public int getItemCount() {
        return weatherDataSet.size();
    }
}
