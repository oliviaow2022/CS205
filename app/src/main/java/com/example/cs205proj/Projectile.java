package com.example.cs205proj;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Projectile {
    int x;
    int y;
    int width = 20;
    int height = 20;
    private final Player player;
    private final PlayerHealth playerHealth;
    Rect rect;
    int displacement;
    int speed = 1;
    String direction;
    int maxDisplacement = 1000;
    boolean isAlive = true;
    public Projectile(int x, int y, String direction, Player player, PlayerHealth playerHealth) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.player = player;
        this.playerHealth = playerHealth;
        rect = new Rect(x, y, x + width, y + height);
    }

    public void draw(Canvas canvas, Paint paint) {
        if (isAlive) {
            paint.setColor(Color.GREEN);
            canvas.drawRect(x, y, x + width, y + height, paint);
        }
    }

    public void update(long deltaTime) {
        handlePlayerCollision();

        displacement += speed * deltaTime;

        // destroy projectile
        if (displacement > maxDisplacement) {
            isAlive = false;
        }

        if (direction.equals("up")) {
            y -= speed * deltaTime;
        } else if (direction.equals("down")) {
            y += speed * deltaTime;
        } else if (direction.equals("left")) {
            x -= speed * deltaTime;
        } else if (direction.equals("right")) {
            x += speed * deltaTime;
        }

        rect.set(x, y, x + width, y + height);
    }

    public void handlePlayerCollision() {
        if (!player.isInvulnerable() && this.isAlive && Rect.intersects(player.getRect(), this.rect)) {
            playerHealth.decrement();
            player.goInvulnerable(2000);
        }

        if (player.getHitbox().isActivated && Rect.intersects(player.getHitbox().rect, this.rect)) {
            isAlive = false;
        }
    }
}
