package com.example.cs205proj;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Player extends Entity {
    int maxV = 10;

    public Player(int x, int y) {
        super();
        this.x = x;
        this.y = y;
        this.width = 50;
        this.height = 50;
        this.velocityX = 0;
        this.velocityY = 0;
    }

    public void draw(Canvas canvas, Paint paint) {
        // player is currently a circle
        paint.setColor(Color.WHITE);
        canvas.drawCircle(x, y, 50, paint);
    }

    public void update(Joystick joystick, Rect display) {
        velocityX = joystick.x - joystick.joystickCenterX;
        velocityY = joystick.y - joystick.joystickCenterY;

        if (velocityX < 0) {
            x = Math.max(display.left + width, x - Math.min(maxV,-velocityX));
        } else {
            x = Math.min(display.right - width, x + Math.min(maxV,velocityX));
        }

        if (velocityY < 0) {
            y = Math.max(display.top + height, y - Math.min(maxV,-velocityY));
        } else {
            y = Math.min(display.bottom - height, y + Math.min(maxV,velocityY));
        }
    }
}
