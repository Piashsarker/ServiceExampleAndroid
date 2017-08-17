package com.dcastalia.serviceexampleandroid;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class WeatherService extends Service {

    // Store the weather data.
    private static final Map<String, String> weatherData = new HashMap<String, String>();
    private static String LOG_TAG = "WeatherService";
    private final IBinder binder = new LocalWeatherBinder();

    public class LocalWeatherBinder extends Binder {

        public WeatherService getService() {
            return WeatherService.this;
        }
    }

    public WeatherService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(LOG_TAG, "onBind");
        return this.binder;
    }

    @Override
    public void onRebind(Intent intent) {
        Log.i(LOG_TAG, "onRebind");
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(LOG_TAG, "onUnbind");
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(LOG_TAG, "onDestroy");
    }

    // Returns the weather information corresponding to the location of the current date.
    public String getWeatherToday(String location) {
        Date now = new Date();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

        String dayString = df.format(now);
        String keyLocAndDay = location + "$" + dayString;

        String weather = weatherData.get(keyLocAndDay);
        //
        if (weather != null) {
            return weather;
        }

        //
        String[] weathers = new String[]{"Rainy", "Hot", "Cool", "Warm", "Snowy"};

        // Random value from 0 to 4
        int i = new Random().nextInt(5);

        weather = weathers[i];
        weatherData.put(keyLocAndDay, weather);
        //
        return weather;
    }



}
