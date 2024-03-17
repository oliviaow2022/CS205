package com.example.cs205proj;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Player {
    private int x;
    private int y;
    private int width = 50;
    private int height = 50;
    private int velocityX;
    private int velocityY;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
        this.velocityX = 0;
        this.velocityY = 0;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(int velocityX) {
        this.velocityX = velocityX;
    }

    public int getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(int velocityY) {
        this.velocityY = velocityY;
    }

    public void draw(Canvas canvas, Paint paint) {
        // player is currently a circle
        paint.setColor(Color.WHITE);
        canvas.drawCircle(x, y, 50, paint);
    }

    public void update(Joystick joystick, Rect display) {
        velocityX = joystick.getX() - joystick.getJoystickCenterX();
        velocityY = joystick.getY() - joystick.getJoystickCenterY();

        if (velocityX < 0) {
            x = Math.max(display.left + width, x + velocityX);
        } else {
            x = Math.min(display.right - width, x + velocityX);
        }

        if (velocityY < 0) {
            y = Math.max(display.top + height, y + velocityY);
        } else {
            y = Math.min(display.bottom - height, y + velocityY);
        }
    }
}
