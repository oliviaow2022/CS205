package com.example.cs205proj;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * PlayerHealth manages player's health and renders it on screen
 * Health is locked by a mutex
 */
public class PlayerHealth {
    private final Bitmap emptyHeart;
    private final Bitmap fullHeart;
    private final Rect heartRect;
    private int x = 100;
    private int y = 200;
    private int heartWidth = 80;
    private int health;
    private final int maxHealth = 5;
    private Lock mutex = new ReentrantLock();

    public PlayerHealth() {
        this.health = maxHealth;

        Context context = GlobalContext.getInstance().getContext();
        emptyHeart = BitmapFactory.decodeResource(context.getResources(), R.drawable.empty_heart);
        fullHeart = BitmapFactory.decodeResource(context.getResources(), R.drawable.full_heart);
        heartRect = new Rect(0,0, emptyHeart.getWidth(), emptyHeart.getHeight());
    }

    public void decrement (){
        mutex.lock();
        try {
            health = Math.max(0, health-1);
        } finally {
            mutex.unlock();
        }
    }

    public void draw(Canvas canvas, Paint paint) {
        for (int i = 0; i < health; i++) {
            canvas.drawBitmap(fullHeart, heartRect, new Rect(x + heartWidth*i, y, x + heartWidth*(i+1), y + heartWidth), paint);
        }
        for (int i = health; i < maxHealth; i++) {
            canvas.drawBitmap(emptyHeart, heartRect, new Rect(x + heartWidth*i, y, x + heartWidth*(i+1), y + heartWidth), paint);
        }
    }

    public int getHealth(){
        return this.health;
    }
}
