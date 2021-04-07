package com.example.cs246_06_prove;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class WeatherForecast {
    private Map<String, Object> city;
    @SerializedName("list")
    private List<WeatherForecastItem> itemList;

    public String getName() {
        return city.get("name").toString();
    }

    public Map<String, Object> getCity() {
        return city;
    }
    public List<WeatherForecastItem> getItemList() {
        return itemList;
    }
}
