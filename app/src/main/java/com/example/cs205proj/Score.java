package com.example.cs205proj;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import androidx.core.content.res.ResourcesCompat;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Score {
    private int value;
    private final Context context;
    private final Typeface typeface;
    private Lock mutex = new ReentrantLock();
//    default
    public Score (Context context){
        this(0, context);
    }

//    use if we want to restore game?
    public Score(int initialValue, Context context){
        this.value = initialValue;
        this.context = context;
        this.typeface = ResourcesCompat.getFont(context, R.font.space_normal);
    }

    public int getValue(){
        mutex.lock();
        try {
            return value;
        } finally {
            mutex.unlock();
        }
    }

//    REQUIREMENT: threads synchronise updates to a common state with built-in synchronisation primitives, e.g. mutexes
//    updated by enemy threads
    public void increment (){
        mutex.lock();
        try {
            value++;
        } finally {
            mutex.unlock();
        }
    }

    public void draw(Canvas canvas, Paint paint){
        //    offset from top right of screen
        final int OFFSETX = 100;
        final int OFFSETY = 150;
        paint.setColor(Color.WHITE);
        paint.setTypeface(typeface);
        paint.setTextScaleX(0.5f);
        paint.setTextSize(100f);
        canvas.drawText("score: " + this.value, OFFSETX, OFFSETY, paint);
    }
}
