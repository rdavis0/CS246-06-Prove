package com.example.cs246_06_prove;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final short TEMP = 1;
    private final short FORECAST = 2;
    private final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void tempButtonPressed(View view) {
        Intent intent = getIntent();
        EditText editText = (EditText) findViewById(R.id.editCity);
        String city = editText.getText().toString();
        WeatherAPIHelper weatherAPIHelper = new WeatherAPIHelper(this, city, TEMP);
        Thread thread = new Thread(weatherAPIHelper);
        thread.start();
    }

    public void forecastButtonPressed(View view) {
        EditText editText = (EditText) findViewById(R.id.editCity);
        String city = editText.getText().toString();
        WeatherAPIHelper weatherAPIHelper = new WeatherAPIHelper(this, city, FORECAST);
        Thread thread = new Thread(weatherAPIHelper);
        thread.start();
    }

    public class WeatherAPIHelper implements Runnable {
        private final String KEY = "88b7cdbe340ca5fb922d38d827eea583";
        private final String BASE_URL = "https://api.openweathermap.org/data/2.5/";
        private WeakReference<Activity> activityRef;
        private String city;
        HTTPHelper http;
        short call;

        public WeatherAPIHelper(Activity activity, String city, short call) {
            this.activityRef = new WeakReference<Activity>(activity);
            this.city = city;
            http = new HTTPHelper();
            this.call = call;
        }

        public String makeCall(HTTPHelper http, String city, String call, List<String> parameters) {
            StringBuilder paramString = new StringBuilder();
            if(parameters != null) {
                for (String s : parameters) {
                    paramString.append("&" + s);
                }
            }
            String query = call + "?q=" + city + "&apiKey=" + KEY + paramString.toString();
            return http.readHTTP(BASE_URL + query);
        }

        public WeatherConditions getTemp (HTTPHelper http, String city, List<String> parameters) {
            Log.d(TAG, "Getting temperature for " + city + " on a background thread...");
            String result = makeCall(http, city, "weather", parameters);
            Gson gson = new Gson();
            return gson.fromJson(result, WeatherConditions.class);
        }

        public WeatherForecast callForecast (HTTPHelper http, String city, List<String> parameters) {
            String result = makeCall(http, city, "forecast", parameters);
            Gson gson = new Gson();
            return gson.fromJson(result, WeatherForecast.class);
        }

        public void run() {
            ArrayList parameters = new ArrayList<String>();
            parameters.add("units=imperial");
            final Activity activity = activityRef.get();

            if(call == TEMP) {
                String temp = getTemp(http, city, parameters).getMeasurements().get("temp").toString();
                String message = "Current temperature in " + city + ": " + temp;
                Log.d(TAG, message);

                if (activity != null) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
            else if(call == FORECAST) {
                //TODO: To finish, use ArrayAdapter to translate Forecast (as a List) to ListView
                callForecast(http, city, parameters).getItemList();
                String message = "";

                if (activity != null) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            // Show Toast

        }
    }


}