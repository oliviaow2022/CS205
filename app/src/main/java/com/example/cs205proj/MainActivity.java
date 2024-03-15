package com.example.cs205proj;

import android.app.Activity;
import android.os.Bundle;
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GameView gameView = new GameView(this);
        setContentView(gameView);
    }
}
