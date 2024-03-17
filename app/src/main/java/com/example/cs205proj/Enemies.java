package com.example.cs205proj;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Enemies {
    Enemy [] enemies;
    int number;
    private final Player player;
    public Enemies(int number, Rect display, Player player) {
        this.enemies = new Enemy[number];
        this.number = number;
        this.player = player;
        for (int i = 0; i < number; i++) {
            int x_pos = (int)Math.round(Math.random() * display.width());
            int y_pos = (int)Math.round(Math.random() * display.height());
            this.enemies[i] = new Enemy(x_pos, y_pos, player);
        }
    }

    public void draw(Canvas canvas, Paint paint) {
        // player is currently a circle
        for (int i = 0; i < number; i++) {
            enemies[i].draw(canvas, paint);
        }
    }
    public void update(Rect display){
        for (int i = 0; i < number; i++) {
            enemies[i].update(display);
        }
    }
}
