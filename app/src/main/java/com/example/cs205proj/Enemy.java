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
    final Player player;
    private final Score score;
    long movementTimer;
    int movementDuration;
    int walkSpeed = 1;
    String[] directions = {"left", "right", "up", "down"};
    Rect rect;
    PlayerHealth playerHealth;
    ArrayList<Projectile> projectileList = new ArrayList<>();
    EnemyFrames enemyFrames;
    final EnemyStateMachine enemyStateMachine;
    public Enemy(int x, int y, Player player, Score score, EnemyFrames enemyFrames, PlayerHealth playerHealth) {
        super();
        this.x = x;
        this.y = y;
        this.width = 200;
        this.height = 225;
        this.velocityX = 0;
        this.velocityY = 0;
        this.player = player;
        this.score = score;
        this.playerHealth = playerHealth;
        this.enemyFrames = enemyFrames;
        this.enemyStateMachine = new EnemyStateMachine(this, enemyFrames);
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
        enemyStateMachine.draw(canvas, paint);
   }

    public void update(long deltaTime, Rect display) {

        // enemy AI for random movement
        if (movementTimer > movementDuration) {
            movementTimer = 0;
            movementDuration = (random.nextInt(5) + 1) * 200;
            direction = directions[random.nextInt(directions.length)];
        }

        movementTimer += deltaTime;

        if (direction.equals("left")) {
            x = (int) Math.max(0, x - walkSpeed * deltaTime);
        } else if (direction.equals("right")) {
            x = (int) Math.min(4000 - width, x + walkSpeed * deltaTime);
        } else if (direction.equals("up")) {
            y = (int) Math.max(0, y - walkSpeed * deltaTime);
        } else if (direction.equals("down")) {
            y = (int) Math.min(2000 - height, y + walkSpeed * deltaTime);
        }

        rect.set(x, y, x + width, y + height);

        // remove unnecessary projectiles
        for (int i = projectileList.size()-1; i >= 0; i--) {
            Projectile projectile = projectileList.get(i);
            projectile.update(deltaTime);

            if (!projectile.isAlive) {
                projectileList.remove(i);
            }
        }

        enemyStateMachine.update(deltaTime);
    }

    @Override
    public void run() {
        // check for collisions
        while (isAlive) {
            if (player.getHitbox().isActivated && Rect.intersects(player.getHitbox().rect, this.rect)) {
                isAlive = false;
                score.increment();
            }

            if (!player.isInvulnerable() && Rect.intersects(player.getRect(), this.rect)) {
                playerHealth.decrement();
                player.goInvulnerable(2000);
            }
        }
    }
}
