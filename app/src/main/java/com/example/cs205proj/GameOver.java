package com.example.cs205proj;

import static com.example.cs205proj.MediaManager.*;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.widget.Button;
import android.view.View;

public class GameOver extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        Button buttonStart = findViewById(R.id.button_start);
        buttonStart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameOver.this, MainActivity.class);
                startActivity(intent);

            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        stopBackgroundMusic();

    }
    @Override
    protected void onResume() {
        super.onResume();
        playOnce(GameOver.this, R.raw.lose_music);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopBackgroundMusic();

        // Release any resources here if needed
    }

}