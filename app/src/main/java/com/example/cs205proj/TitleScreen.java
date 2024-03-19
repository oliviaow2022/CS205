package com.example.cs205proj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class TitleScreen extends AppCompatActivity {

    MediaPlayer titleMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_screen);
        Button buttonStart = findViewById(R.id.button_start);
        titleMusic = MediaPlayer.create(TitleScreen.this, R.raw.title_music);
        titleMusic.setLooping(true);
        titleMusic.start();

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titleMusic.stop();
                Intent intent = new Intent(TitleScreen.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
    @Override
    protected void onPause() {
        super.onPause();
        titleMusic.release();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Stop and release MediaPlayer resources
        if (titleMusic != null) {
            titleMusic.stop();
            titleMusic.release();
            titleMusic = null;
        }
    }
}