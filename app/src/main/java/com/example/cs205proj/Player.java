package com.example.cs205proj;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;

public class Player extends Entity {
    int maxV = 1;
    final Hitbox playerHitbox;
    final PlayerStateMachine playerStateMachine;

    public Player(int x, int y) {
        super();
        this.x = x;
        this.y = y;

        this.health = 4;
        this.maxHealth = 5;

        this.width = 100; //current width of player
        this.height = 200; //current height of player
        this.velocityX = 0;
        this.velocityY = 0;
        this.playerHitbox = new Hitbox(this);
        this.playerStateMachine = new PlayerStateMachine(this);
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
        playerHitbox.draw(canvas, paint);
        playerStateMachine.draw(canvas, paint);
    }

    public void update(long deltaTime, Joystick joystick, Rect display) {
        velocityX = joystick.x - joystick.joystickCenterX;
        velocityY = joystick.y - joystick.joystickCenterY;

        if (Math.abs(velocityX) >= Math.abs(velocityY)) {
            if (velocityX < 0) {
                direction = "left";
                x = (int) Math.max(display.left + width, x - Math.min(maxV,-velocityX) * deltaTime);
            } else if (velocityX > 0){
                direction = "right";
                x = (int) Math.min(display.right - width, x + Math.min(maxV,velocityX) * deltaTime);
            }
        } else {
            if (velocityY < 0) {
                direction = "up";
                y = (int) Math.max(display.top + height, y - Math.min(maxV,-velocityY) * deltaTime);
            } else if (velocityY > 0){
                direction = "down";
                y = (int) Math.min(display.bottom - height, y + Math.min(maxV,velocityY) * deltaTime);
            }
        }

        playerHitbox.update(joystick);
        playerStateMachine.update(deltaTime);
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

    public Hitbox getHitbox(){
        return this.playerHitbox;
    }
}
