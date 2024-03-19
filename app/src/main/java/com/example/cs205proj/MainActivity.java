package com.example.cs205proj;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.media.MediaPlayer;

public class MainActivity extends Activity {
    MediaPlayer backgroundMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GameView gameView = new GameView(this);
        setContentView(gameView);
        backgroundMusic = MediaPlayer.create(MainActivity.this, R.raw.battle_music);
        backgroundMusic.setLooping(true);
        backgroundMusic.start();

    }
    @Override
    protected void onPause() {
        super.onPause();
        backgroundMusic.release();
        finish();
    }
}