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
    DatabaseHelper db;
    Score score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GlobalContext globalContext = GlobalContext.getInstance();
        globalContext.setContext(this);

        db = DatabaseHelper.getInstance(this, "Game");
        player = new Player(390, 0);
        score = new Score(this);
        GameView gameView = new GameView(this, player, score);
        setContentView(gameView);
        // backgroundMusic = MediaPlayer.create(MainActivity.this, R.raw.battle_music);
        // backgroundMusic.setLooping(true);
        // backgroundMusic.start();

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
        //      REQUIREMENT store data into sqlLite
        int current_score = score.getValue();
        int max_score = Math.max(current_score, db.getLatestScore());
        System.out.println("onPause:" + current_score);
        db.insertScore(max_score);
        super.onPause();
        backgroundMusic.release();
    }

    @Override
    protected void onStop() {
        super.onStop();

        //      REQUIREMENT store data into sqlLite
        int current_score = score.getValue();
        int max_score = Math.max(current_score, db.getLatestScore());
        System.out.println("onStop:" + current_score);
        db.insertScore(max_score);

        // Stop and release MediaPlayer resources
        if (backgroundMusic != null) {
            backgroundMusic.stop();
            backgroundMusic.release();
            backgroundMusic = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //      REQUIREMENT store data into sqlLite
        int current_score = score.getValue();
        int max_score = Math.max(current_score, db.getLatestScore());
        System.out.println("onDestroy:" + current_score);
        db.insertScore(max_score);

        // Stop and release MediaPlayer resources
        if (backgroundMusic != null) {
            backgroundMusic.stop();
            backgroundMusic.release();
            backgroundMusic = null;
        }
    }
}