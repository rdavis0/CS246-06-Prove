package com.example.cs246_06_prove;

import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.Map;

public class WeatherForecastItem {
    @SerializedName("dt_txt") private String time;
    @SerializedName("main") private Map<String, Float> measurements;
    private Map<String, String>[] weather;
    private Map<String, Float> wind;

    public String getTime() {
        return time;
    }

    public Map<String, Float> getMeasurements() {
        return measurements;
    }

    public String getFormattedTemp() {
        return measurements.get("temp") + "\u00B0 F";
    }

    public String getFormattedDesc() {
        return StringUtils.capitalize(weather[0].get("description"));
    }

    public Map<String, String>[] getWeather() {
        return weather;
    }

    public float getWindSpeed() {
        return wind.get("speed");
    }

    public float getMaxTemp() { return measurements.get("temp_max"); }

}
