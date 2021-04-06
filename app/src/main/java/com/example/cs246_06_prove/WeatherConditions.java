package com.example.cs246_06_prove;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

public class WeatherConditions {
    private int id;
    private String name;
    @SerializedName("main")
    private Map<String, Float> measurements;

    public WeatherConditions(int id, String name, Map<String, Float> measurements) {
        this.id = id;
        this.name = name;
        this.measurements = measurements;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Map<String, Float> getMeasurements() {
        return measurements;
    }

}
