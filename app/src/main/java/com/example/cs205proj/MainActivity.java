package com.example.cs205proj;

import static com.example.cs205proj.MediaManager.*;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.media.MediaPlayer;

import androidx.annotation.NonNull;

public class MainActivity extends Activity {
    Player player;
    DatabaseHelper db;
    Score score;
    GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GlobalContext globalContext = GlobalContext.getInstance();
        globalContext.setContext(this);
        db = DatabaseHelper.getInstance(this, "Game");
        player = new Player(390, 0, MainActivity.this);
        score = new Score(this);
        if (gameView == null) {
            gameView = new GameView(this, player, score);
        }
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS

        );
        setContentView(gameView);

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
//        backgroundMusic.release();
        System.out.println("Pausing from Main");
        stopBackgroundMusic();
        System.out.println("Exiting Main!");
        gameView.pauseGame();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resumeGame();
        playBackgroundMusic(MainActivity.this, R.raw.battle_music);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Stop and release MediaPlayer resources
        System.out.println("Destroy from Main");
        stopBackgroundMusic();


    }
}