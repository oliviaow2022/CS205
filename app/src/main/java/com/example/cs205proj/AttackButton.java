package com.example.cs205proj;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

public class AttackButton {
    int buttonCenterX;
    int buttonCenterY;
    final int radius = 150; // Radius of the attack button
    Hitbox playerHitbox;
    Bitmap button;
    Bitmap buttonActive;
    Rect src, dest;
    float scaleFactor = (radius - 50) * 2.0f / 300;
    int scaledSpriteSize = (int) (250 * scaleFactor);


    public AttackButton(Context context, Hitbox insertedHitbox) {
        this.playerHitbox = insertedHitbox;
        this.button = BitmapFactory.decodeResource(context.getResources(), R.drawable.attack);
        this.buttonActive = BitmapFactory.decodeResource(context.getResources(), R.drawable.attack1);
        this.src = new Rect(0, 0, button.getWidth(), button.getHeight());
        this.dest = new Rect(buttonCenterX - radius, buttonCenterY - radius,
                buttonCenterX + radius, buttonCenterY + radius);
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
        buttonCenterX = (int)(width - 2*radius);
        buttonCenterY = (int)(height - 50 - 1.5*radius);
        paint.setColor(Color.GRAY); //Placeholder color
        //canvas.drawCircle(buttonCenterX, buttonCenterY, radius, paint);
        dest.set(
                (int) (buttonCenterX - scaledSpriteSize),
                (int) (buttonCenterY - scaledSpriteSize),
                (int) (buttonCenterX + scaledSpriteSize),
                (int) (buttonCenterY + scaledSpriteSize)
        );
        if (playerHitbox.isActivated) {
            canvas.drawBitmap(buttonActive, src, dest, null);

        }
        else {
            canvas.drawBitmap(button, src, dest, null);
        }

    }
}
