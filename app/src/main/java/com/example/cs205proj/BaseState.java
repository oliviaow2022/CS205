package com.example.cs205proj;

import android.graphics.Canvas;
import android.graphics.Paint;

/*
 * BaseState is the parent class for Player and Enemy states
 * This allows for transition between states in the state machines.
 */

public abstract class BaseState {
    public abstract void update(long deltaTime);
    public abstract void draw(Canvas canvas, Paint paint);
}