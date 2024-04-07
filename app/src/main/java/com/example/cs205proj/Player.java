package com.example.cs205proj;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;

public class Player extends Entity {
    int maxV = 10;
    private final Hitbox playerHitbox;
    final PlayerStateMachine playerStateMachine;

    // for collision
    Rect rect;

    // variables to handle invulnerability
    private boolean invulnerable = false;
    int invulnerableDuration = 0;
    long invulnerableTimer = 0;
    long flashTimer = 0;

    public Player(int x, int y, Context context) {
        super();
        this.x = x;
        this.y = y;

        this.width = 100; //current width of player
        this.height = 156; //current height of player
        this.velocityX = 0;
        this.velocityY = 0;
        this.playerHitbox = new Hitbox(context,this);
        this.playerStateMachine = new PlayerStateMachine(this);

        rect = new Rect(x, y, x + width, y + height);
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

        if (!invulnerable) {
            playerStateMachine.draw(canvas, paint);
            return;
        }

        if (invulnerable && flashTimer > 200) {
            flashTimer = 0;
            playerStateMachine.draw(canvas, paint);
        }
    }

    public void update(long deltaTime, Joystick joystick, Background background) {
        velocityX = joystick.x - joystick.joystickCenterX;
        velocityY = joystick.y - joystick.joystickCenterY;
        int absVx = Math.abs(velocityX) / 10;
        int absVy = Math.abs(velocityY) / 10;
        // determine main direction of player
        if (Math.abs(velocityX) >= Math.abs(velocityY)) {
            if (velocityX < 0) {
                direction = "left";
                x = (int) Math.max(0, x - (Math.min(maxV,absVx) * deltaTime) / 8);
            } else if (velocityX > 0){
                direction = "right";
                x = (int) Math.min(background.mapWidth - width, x + (Math.min(maxV,absVx) * deltaTime) / 8);
            }
        } else {
            if (velocityY < 0) {
                direction = "up";
                y = (int) Math.max(0, y - (Math.min(maxV,absVy) * deltaTime) / 8);
            } else if (velocityY > 0){
                direction = "down";
                y = (int) Math.min(background.mapHeight - height, y + ((Math.min(maxV,absVy) * deltaTime) / 8));
            }
        }

        // update invulnerability timers
        if (invulnerable) {
            invulnerableTimer += deltaTime;
            flashTimer += deltaTime;

            if (invulnerableTimer > invulnerableDuration) {
                invulnerable = false;
                invulnerableTimer = 0;
                invulnerableDuration = 0;
                flashTimer = 0;
            }
        }

        playerHitbox.update(joystick);
        playerStateMachine.update(deltaTime);
        rect.set(x, y, x + width, y + height);
    }

    public PlayerStateMachine getPlayerStateMachine() { return this.playerStateMachine; }

    public Hitbox getHitbox(){
        return this.playerHitbox;
    }

    public boolean isInvulnerable() { return this.invulnerable; }

    public Rect getRect() { return this.rect; }

    public void goInvulnerable(int invulnerableDuration) {
        invulnerable = true;
        this.invulnerableDuration = invulnerableDuration;
    }
}
