package com.dcastalia.serviceexampleandroid;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private boolean binded=false;
    private WeatherService weatherService;

    private TextView weatherText;
    private EditText locationText;

    ServiceConnection weatherServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            WeatherService.LocalWeatherBinder binder = (WeatherService.LocalWeatherBinder) service;
            weatherService = binder.getService();
            binded = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            binded = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherText = (TextView) this.findViewById(R.id.text_weather);
        locationText = (EditText)this.findViewById(R.id.text_input_location);
    }

    // When user click on 'see weather' button.
    public void showWeather(View view)  {
        String location = locationText.getText().toString();

        String weather= this.weatherService.getWeatherToday(location);

        weatherText.setText(weather);
    }


    @Override
    protected void onStart() {
        super.onStart();

        // Create Intent object for WeatherService.
        Intent intent = new Intent(this, WeatherService.class);

        // Call bindService(..) method to bind service with UI.
        this.bindService(intent, weatherServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (binded) {
            // Unbind Service
            this.unbindService(weatherServiceConnection);
            binded = false;
        }
    }


    /** This method is called when users click on the Start button.**/
    public void playSong(View view)  {
        // Create Intent object for PlaySongService.
        Intent myIntent = new Intent(MainActivity.this, PlaySongService.class);
        // Call startService with Intent parameter.
        this.startService(myIntent);
    }

    // This method is called when users click on the Stop button.
    public void stopSong(View view)  {

        // Create Intent object
        Intent myIntent = new Intent(MainActivity.this, PlaySongService.class);
        this.stopService(myIntent);
    }



}
