package com.example.cs246_06_prove;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.ref.WeakReference;

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
        private WeakReference<Activity> activityRef;
        private String city;

        public WeatherAPIHelper(Activity activity, String city) {
            this.activityRef = new WeakReference<Activity>(activity);
            this.city = city;
        }

        public void getTemp() {
            Log.d(TAG, "Getting temperature for " + city + " on a background thread...");
        }

        public void run() {
            getTemp();

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