package com.dcastalia.serviceexampleandroid;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class PlaySongService extends Service {

    private MediaPlayer mediaPlayer;

    public PlaySongService() {

    }



    @Override
    public IBinder onBind(Intent intent){
        // This service is unbounded
        // So this method is never called.
        return null;
    }


    @Override
    public void onCreate(){
        super.onCreate();
        // Create MediaPlayer object, to play your song.
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.music_1);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        // Play song.
        mediaPlayer.start();

        return START_STICKY;
    }

    // Destroy
    @Override
    public void onDestroy() {
        // Release the resources
        mediaPlayer.release();
        super.onDestroy();
    }
}
