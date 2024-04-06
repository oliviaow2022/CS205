package com.example.cs205proj;

import static com.example.cs205proj.MediaManager.*;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class GameWin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("Hello from Win!");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_win);
        Button buttonStart = findViewById(R.id.button_start);
        System.out.println("Playing Win!");
        buttonStart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameWin.this, MainActivity.class);
                startActivity(intent);

            }
        });

    }
    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("Pausing from Win");
        stopBackgroundMusic();
        // Release any resources here if needed
    }

    @Override
    protected void onStop() {
        super.onStop();
//        System.out.println("Stopping from Win");
//        stopBackgroundMusic();
        // Release any resources here if needed
    }
    @Override
    protected void onResume() {
        super.onResume();
        playOnce(GameWin.this, R.raw.win_music);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("Destroying from Win");
        stopBackgroundMusic();
        // Release any resources here if needed
    }

}