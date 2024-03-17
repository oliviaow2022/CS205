package com.example.cs205proj;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

public class Joystick {
    private final int joystickCenterX = 200;
    private final int joystickCenterY = 2000;
    private final int radius = 150; // Radius of the joystick
    private final int margin = 50; // Margin between joystick and screen edges

    private int x = 200;
    private int y = 2000;

    public Joystick() {}

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getJoystickCenterX() {
        return joystickCenterX;
    }

    public int getJoystickCenterY() {
        return joystickCenterY;
    }

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

    public void draw(Canvas canvas, Paint paint) {
        // player is currently a circle
        paint.setColor(Color.WHITE);
        canvas.drawCircle(joystickCenterX, joystickCenterY, radius, paint);
        paint.setColor(Color.BLACK);
        canvas.drawCircle(x, y,50, paint); // Draw center of joyStick
    }
}
