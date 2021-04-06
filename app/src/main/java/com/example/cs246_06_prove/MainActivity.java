package com.example.cs246_06_prove;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.lang.ref.WeakReference;
import java.util.List;

public class MainActivity extends AppCompatActivity {
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
        WeatherAPIHelper weatherAPIHelper = new WeatherAPIHelper(this, city);
        weatherAPIHelper.run();
        Toast.makeText(this, "Get temp for " + city, Toast.LENGTH_SHORT).show();
    }

    public void forecastButtonPressed(View view) {
        EditText editText = (EditText) findViewById(R.id.editCity);
        String city = editText.getText().toString();
        Toast.makeText(this, "Get forecast for " + city, Toast.LENGTH_SHORT).show();
    }

    public class WeatherAPIHelper implements Runnable {
        private final String KEY = "88b7cdbe340ca5fb922d38d827eea583";
        private final String BASE_URL = "https://api.openweathermap.org/data/2.5/";
        private WeakReference<Activity> activityRef;
        private String city;
        HTTPHelper http;

        public WeatherAPIHelper(Activity activity, String city) {
            this.activityRef = new WeakReference<Activity>(activity);
            this.city = city;
            http = new HTTPHelper();
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

//        public WeatherForecast callForecast (HTTPHelper http, String city, List<String> parameters) {
//            String result = makeCall(http, city, "forecast", parameters);
//            Gson gson = new Gson();
//            return gson.fromJson(result, WeatherForecast.class);
//        }

        public void run() {
            getTemp(http, city, null);

//            final Activity activity = activityRef.get();
//
//            if (activity != null) {
//                activity.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(getApplicationContext(), "Odds complete", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
        }
    }


}