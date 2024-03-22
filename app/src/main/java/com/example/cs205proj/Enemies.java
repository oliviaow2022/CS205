package com.example.cs205proj;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Enemies {

//    REQUIREMENT: EACH ENEMY IS A THREAD, THREAD POOL CURRENT SIZE = 5
    EnemiesThreadPool enemies;
    int number;
    private final Player player;
    Rect display;
    private final Score score;
    public Enemies(int number, Rect display, Player player, Score score) {
        this.display = display;
        this.number = number;
        this.player = player;
        this.score = score;
        enemies = new EnemiesThreadPool(number, number, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
        for (int i = 0; i < number; i++) {
            int x_pos = (int) Math.round(Math.random() * display.width());
            int y_pos = (int) Math.round(Math.random() * display.height());
            enemies.executeEnemy(new Enemy(x_pos, y_pos, player, score));
        }
    }
    public void draw(Canvas canvas, Paint paint) {
        Set<Enemy> activeEnemies = enemies.getActiveTasks();
        paint.setColor(Color.RED);
        for (Enemy enemy : activeEnemies) {
//            System.out.println("enemies Draw" + enemy.getX());
            canvas.drawCircle(enemy.getX(), enemy.getY(), 50, paint);
        }
    }

    public void update(Rect display) {
        Set<Enemy> activeEnemies = enemies.getActiveTasks();
        for (Enemy enemy : activeEnemies) {
            enemy.update();
        }
        if (enemies.hasAvailableSpot()) {
            int x_pos = (int) Math.round(Math.random() * display.width());
            int y_pos = (int) Math.round(Math.random() * display.height());
            enemies.executeEnemy(new Enemy(x_pos, y_pos, player, score));
        }
    }
}

