package com.example.cs205proj;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.media.MediaPlayer;

import androidx.annotation.NonNull;

public class MainActivity extends Activity {
    MediaPlayer backgroundMusic;
    Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GlobalContext globalContext = GlobalContext.getInstance();
        globalContext.setContext(this);

        player = new Player(100, 100);
        GameView gameView = new GameView(this, player);
        setContentView(gameView);
        backgroundMusic = MediaPlayer.create(MainActivity.this, R.raw.battle_music);
        backgroundMusic.setLooping(true);
        backgroundMusic.start();

        if (savedInstanceState != null) {
            player.restoreInstanceState(savedInstanceState);
        }
    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        player.saveInstanceState(outState);
    }
    @Override
    protected void onPause() {
        super.onPause();
        backgroundMusic.release();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Stop and release MediaPlayer resources
        if (backgroundMusic != null) {
            backgroundMusic.stop();
            backgroundMusic.release();
            backgroundMusic = null;
        }
    }
}