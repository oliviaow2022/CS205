package com.example.cs205proj;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;

public class Player extends Entity {
    int maxV = 10;
    hitBox playerHitBox = null;

    public Player(int x, int y) {
        super();
        this.x = x;
        this.y = y;
        this.width = 50;
        this.height = 50;
        this.velocityX = 0;
        this.velocityY = 0;
        playerHitBox = new hitBox(this);
    }

    public void saveInstanceState(Bundle outState) {
        outState.putInt("player_x", x);
        outState.putInt("player_y", y);
    }

    // Restore player state from a Bundle
    public void restoreInstanceState(Bundle savedInstanceState) {
        x = savedInstanceState.getInt("player_x");
        y = savedInstanceState.getInt("player_y");
    }

    public void draw(Canvas canvas, Paint paint) {
        // player is currently a circle
        paint.setColor(Color.WHITE);
        canvas.drawCircle(
                x,
                y,
                50,
                paint);
        playerHitBox.draw(canvas, paint);
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
        playerHitBox.update(joystick);
    }

    public int getVelocityX(){
        return this.velocityX;
    }

    public int getVelocityY(){
        return this.velocityY;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }
}
