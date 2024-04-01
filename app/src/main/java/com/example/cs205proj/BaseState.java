package com.example.cs205proj;

import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class BaseState {
    public abstract void update(long deltaTime);
    public abstract void draw(Canvas canvas, Paint paint);
}