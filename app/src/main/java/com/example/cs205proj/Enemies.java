package com.example.cs205proj;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Enemies {

//    REQUIREMENT: EACH ENEMY IS A THREAD, THREAD POOL CURRENT SIZE = 5
    EnemiesThreadPool enemies;
    int number;
    private final Player player;
    Rect display;
    Background background;
    private final Score score;

    private final PlayerHealth playerHealth;

    private final EnemyFrames enemyFrames = new EnemyFrames();

    public Enemies(int number, Rect display, Player player, Score score, PlayerHealth playerHealth, Background background) {
        this.display = display;
        this.background = background;
        this.number = number;
        this.player = player;
        this.score = score;
        this.playerHealth = playerHealth;

        enemies = new EnemiesThreadPool(number, number, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
        for (int i = 0; i < number; i++) {
            int x_pos = (int) Math.round(Math.random() * display.width());
            int y_pos = (int) Math.round(Math.random() * display.height());
            enemies.executeEnemy(new Enemy(x_pos, y_pos, player, score, enemyFrames, playerHealth));
        }
    }
    public void draw(Canvas canvas, Paint paint) {
        Set<Enemy> activeEnemies = enemies.getActiveTasks();
        paint.setColor(Color.RED);
        for (Enemy enemy : activeEnemies) {
            enemy.draw(canvas, paint);
        }

        // canvas.drawBitmap(enemyFrames.leftFrames[3], null, new Rect(player.x, player.y, player.x + 145, player.y + 200), paint);
    }

    public void update(long deltaTime, Rect display) {
        Set<Enemy> activeEnemies = enemies.getActiveTasks();
        for (Enemy enemy : activeEnemies) {
            enemy.update(deltaTime, display);
        }
        if (enemies.hasAvailableSpot()) {
            int x_pos = (int) Math.round(Math.random() * display.width());
            int y_pos = (int) Math.round(Math.random() * display.height());
            enemies.executeEnemy(new Enemy(x_pos, y_pos, player, score, enemyFrames, playerHealth));
        }
    }
    public void end (){
//        System.out.println("shutting down");
        enemies.shutdownNow();
    }
}

