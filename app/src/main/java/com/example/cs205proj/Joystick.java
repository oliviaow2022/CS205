package com.example.cs205proj;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

public class Joystick {
    int joystickCenterX = 200;
    int joystickCenterY = 2000;
    final int radius = 150; // Radius of the joystick

    int x = 200;
    int y = 2000;

    public Joystick() {}

    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float touchX = event.getX();
        float touchY = event.getY();
        float distanceX = touchX - joystickCenterX;
        float distanceY = touchY - joystickCenterY;
        double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);

        if (action == MotionEvent.ACTION_MOVE){
            if (distance <= radius - 50){
                x = (int) touchX;
                y = (int) touchY;
            }
        }
        if (action == MotionEvent.ACTION_UP){
            x = joystickCenterX;
            y = joystickCenterY;
        }
        return true;
    }

    public void draw(Canvas canvas, Paint paint, int height) {
        // player is currently a circle
        joystickCenterX = 50 + radius;
        joystickCenterY = height - 50 - radius;
        paint.setColor(Color.WHITE);
        canvas.drawCircle(joystickCenterX, joystickCenterY, radius, paint);
        paint.setColor(Color.BLACK);
        canvas.drawCircle(x, y,50, paint); // Draw center of joyStick
    }
}
