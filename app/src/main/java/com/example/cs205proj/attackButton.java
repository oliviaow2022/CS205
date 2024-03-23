package com.example.cs205proj;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

public class attackButton {
    int buttonCenterX;
    int buttonCenterY;
    final int radius = 100; // Radius of the attack button
    hitBox playerHitbox;
    int Height;


    public attackButton(hitBox insertedHitbox) {
        this.playerHitbox = insertedHitbox;
    }

    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float touchX = event.getX();
        float touchY = event.getY();
        float distanceX = touchX - buttonCenterX;
        float distanceY = touchY - buttonCenterY;
        double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);

        if (action == MotionEvent.ACTION_DOWN){
            if (distance <= radius){
                playerHitbox.activateHitbox();
                System.out.println("ATTACK");
            } 
        }
        return true;
    }

    public void draw(Canvas canvas, Paint paint, int height) {
        // player is currently a circle
        this.Height = height;
        buttonCenterX = (int)(1700 + 2*radius);
        buttonCenterY = (int)(height - 50 - 1.5*radius);
        paint.setColor(Color.GREEN);
        canvas.drawCircle(buttonCenterX, buttonCenterY, radius, paint);
    }

}
