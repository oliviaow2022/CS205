package com.example.cs205proj;

import java.util.*;
import static java.lang.Thread.sleep;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;

public class Enemy extends Entity implements Runnable {
    Random random = new Random();
    boolean isAlive = true;
    private final Player player;
    private final Score score;
    long movementTimer;
    int movementDuration;
    Animation[] animations;
    int walkSpeed = 1;
    String[] directions = {"left", "right", "up", "down"};
    Rect rect;
    PlayerHealth playerHealth;
    int currentAnimation = 0;
    ArrayList<Projectile> projectileList = new ArrayList<>();

    public Enemy(int x, int y, Player player, Score score, Animation[] animations, PlayerHealth playerHealth) {
        super();
        this.x = x;
        this.y = y;
        this.width = 120;
        this.height = 200;
        this.velocityX = 0;
        this.velocityY = 0;
        this.player = player;
        this.score = score;
        this.playerHealth = playerHealth;
        this.animations = animations;
        this.rect = new Rect(x, y, x + width, y + height);
    }
    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public void draw(Canvas canvas, Paint paint) {
        for (Projectile projectile : projectileList) {
            projectile.draw(canvas, paint);
        }
        canvas.drawBitmap(animations[currentAnimation].getCurrentFrame(), null, new Rect(x, y, x + width, y + height), paint);
   }

    public void update(long deltaTime, Rect display) {
        if (movementTimer > movementDuration) {
            movementTimer = 0;
            movementDuration = (random.nextInt(5) + 1) * 100;
            direction = directions[random.nextInt(directions.length)];
        }

        movementTimer += deltaTime;

        if (direction.equals("left")) {
            currentAnimation = 1;
            x = (int) Math.max(display.left + width, x - walkSpeed * deltaTime);
        } else if (direction.equals("right")) {
            currentAnimation = 0;
            x = (int) Math.min(display.right - width, x + walkSpeed * deltaTime);
        } else if (direction.equals("up")) {
            y = (int) Math.max(display.top + height, y - walkSpeed * deltaTime);
        } else if (direction.equals("down")) {
            y = (int) Math.min(display.bottom - height, y + walkSpeed * deltaTime);
        }

        rect.set(x, y, x + width, y + height);
        animations[currentAnimation].update(deltaTime);

        // generate projectile with certain probability
        double randomNumber = Math.random();
        if (randomNumber < 0.05 && (direction.equals("left") || direction.equals("right"))) {
            projectileList.add(new Projectile(x, y + (height / 2), direction, player, playerHealth));
        }

        for (int i = projectileList.size()-1; i >= 0; i--) {
            Projectile projectile = projectileList.get(i);
            projectile.update(deltaTime);

            if (!projectile.isAlive) {
                projectileList.remove(i);
            }
        }
    }

    @Override
    public void run() {
        // check for collisions
        while (isAlive) {
            if (player.playerHitbox.isActivated && Rect.intersects(player.playerHitbox.rect, this.rect)) {
                isAlive = false;
                score.increment();
            }

            if (!player.invulnerable && Rect.intersects(player.rect, this.rect)) {
                playerHealth.decrement();
                player.goInvulnerable(2000);
            }
        }
    }
}
