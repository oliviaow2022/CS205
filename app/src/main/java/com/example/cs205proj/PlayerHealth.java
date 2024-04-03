package com.example.cs205proj;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PlayerHealth {
    private final Bitmap scaledFullHeart;
    private final Bitmap scaledEmptyHeart;
    private int x;
    private int y;
    private int health;
    private final int maxHealth = 5;

    private Lock mutex = new ReentrantLock();

    public PlayerHealth() {
        this.health = maxHealth;

        Context context = GlobalContext.getInstance().getContext();
        Bitmap heartsSpriteSheet = BitmapFactory.decodeResource(context.getResources(), R.drawable.hearts);
        Bitmap emptyHeart = Bitmap.createBitmap(heartsSpriteSheet, 2, 0, 43, 43);
        Bitmap fullHeart = Bitmap.createBitmap(heartsSpriteSheet, 178, 0, 43, 43);
        scaledFullHeart = Bitmap.createScaledBitmap(fullHeart, 100, 100,true);
        scaledEmptyHeart = Bitmap.createScaledBitmap(emptyHeart, 100, 100, true);
    }

    public void decrement (){
        mutex.lock();
        try {
            health = Math.max(0, health-1);
        } finally {
            mutex.unlock();
        }
    }

    public void draw(Canvas canvas, Paint paint, int canvasWidth) {
        this.x = 100;
        this.y = 150;

        for (int i = 0; i < health; i++) {
            canvas.drawBitmap(scaledFullHeart, null, new Rect(x + 100*i, y, x + 100*(i+1), y+100), paint);
        }
        for (int i = health; i < maxHealth; i++) {
            canvas.drawBitmap(scaledEmptyHeart, null, new Rect(x+100*i, y, x+100*(i+1), y+100), paint);
        }
    }
}
