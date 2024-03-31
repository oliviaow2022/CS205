package com.example.cs205proj;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

    private final PlayerHealth playerHealth;
    Bitmap[] rightFrames = new Bitmap[4];

    Bitmap[] leftFrames = new Bitmap[4];

    public Enemies(int number, Rect display, Player player, Score score, PlayerHealth playerHealth) {
        this.display = display;
        this.number = number;
        this.player = player;
        this.score = score;
        this.playerHealth = playerHealth;

        Context context = GlobalContext.getInstance().getContext();
        Bitmap spriteSheetRight = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy_robot);
        int[] xCoordinateRight = {80, 340, 610, 870};

        for (int i = 0; i < xCoordinateRight.length; i++) {
            Bitmap frame = Bitmap.createBitmap(spriteSheetRight, xCoordinateRight[i], 0, 120, 200);
            rightFrames[i] = Bitmap.createScaledBitmap(frame, 120, 200, true);
        }

        Bitmap spriteSheetLeft = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy_robot_flip);
        int[] xCoordinateLeft = {1640, 1910, 2170, 240};

        for (int i = 0; i < xCoordinateLeft.length; i++) {
            Bitmap frame = Bitmap.createBitmap(spriteSheetLeft, xCoordinateLeft[i], 0, 120, 200);
            leftFrames[i] = Bitmap.createScaledBitmap(frame, 120, 200, true);
        }

        enemies = new EnemiesThreadPool(number, number, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
        for (int i = 0; i < number; i++) {
            int x_pos = (int) Math.round(Math.random() * display.width());
            int y_pos = (int) Math.round(Math.random() * display.height());
            Animation[] animations = {new Animation(rightFrames, true, 200), new Animation(leftFrames, true, 200)};
            enemies.executeEnemy(new Enemy(x_pos, y_pos, player, score, animations, playerHealth));
        }
    }
    public void draw(Canvas canvas, Paint paint) {
        Set<Enemy> activeEnemies = enemies.getActiveTasks();
        paint.setColor(Color.RED);
        for (Enemy enemy : activeEnemies) {
            enemy.draw(canvas, paint);
        }
    }

    public void update(long deltaTime, Rect display) {
        Set<Enemy> activeEnemies = enemies.getActiveTasks();
        for (Enemy enemy : activeEnemies) {
            enemy.update(deltaTime, display);
        }
        if (enemies.hasAvailableSpot()) {
            int x_pos = (int) Math.round(Math.random() * display.width());
            int y_pos = (int) Math.round(Math.random() * display.height());
            Animation[] animations = {new Animation(rightFrames, true, 200), new Animation(leftFrames, true, 200)};
            enemies.executeEnemy(new Enemy(x_pos, y_pos, player, score, animations, playerHealth));
        }
    }
}

