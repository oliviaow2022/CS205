package com.example.cs205proj;

import android.content.Context;
import android.media.MediaPlayer;

import android.content.Context;
import android.media.MediaPlayer;

public class MediaManager {

    private static MediaPlayer mediaPlayer;
    private static final Object lock = new Object();

    public static void playBackgroundMusic(Context context, int resourceId) {
        synchronized (lock) {
            releaseMediaPlayer();
            mediaPlayer = MediaPlayer.create(context.getApplicationContext(), resourceId);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }
    }

    public static void playOnce(Context context, int resourceId) {
        synchronized (lock) {
            releaseMediaPlayer();
            mediaPlayer = MediaPlayer.create(context.getApplicationContext(), resourceId);
            mediaPlayer.setLooping(false);
            mediaPlayer.start();
        }
    }

    public static void stopBackgroundMusic() {
        synchronized (lock) {
            releaseMediaPlayer();
        }
    }

    private static void releaseMediaPlayer() {
        synchronized (lock) {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.reset();
                mediaPlayer.release();
                mediaPlayer = null;
            }
        }
    }
}

