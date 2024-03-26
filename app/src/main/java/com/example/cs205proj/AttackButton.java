package com.example.cs205proj;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

public class AttackButton {
    int buttonCenterX;
    int buttonCenterY;
    final int radius = 150; // Radius of the attack button
    Hitbox playerHitbox;
    int Height;
    int Width;


    public AttackButton(Hitbox insertedHitbox) {
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
            } 
        }
        return true;
    }

    public void draw(Canvas canvas, Paint paint, int height, int width) {
        // player is currently a circle
        this.Height = height;
        this.Width = width;
        buttonCenterX = (int)(width - 2*radius);
        buttonCenterY = (int)(height - 50 - 1.5*radius);
        paint.setColor(Color.GREEN); //Placeholder color
        canvas.drawCircle(buttonCenterX, buttonCenterY, radius, paint);
    }

}
