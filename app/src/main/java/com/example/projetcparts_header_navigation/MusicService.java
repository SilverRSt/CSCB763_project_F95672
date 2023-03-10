package com.example.projetcparts_header_navigation;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

/**
 * Music service.
 */
public class MusicService extends Service {
    private MediaPlayer player;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    /**
     * Get service by id, set it to loop and start it.
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.player = MediaPlayer.create(this, R.raw.button_music);
        this.player.setLooping(Boolean.TRUE);
        this.player.start();

        return START_STICKY;
    }


    /**
     * Stop the service.
     */
    @Override
    public void onDestroy() {
        player.stop();
        super.onDestroy();
    }
}
