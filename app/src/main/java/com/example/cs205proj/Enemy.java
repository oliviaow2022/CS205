package com.example.cs205proj;

import static java.lang.Thread.sleep;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;

public class Enemy extends Entity implements Runnable{
    int MAX_SPEED = 10;
//    int VISIBILITY = 2000;
    int VISIBILITY = (int)Double.POSITIVE_INFINITY;
    boolean isAlive = true;
    private final Player player;
    public Enemy(int x, int y, Player player) {
        super();
        this.x = x;
        this.y = y;
        this.width = 50;
        this.height = 50;
        this.velocityX = 0;
        this.velocityY = 0;
        this.player = player;
    }
    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }
//    public void draw(Canvas canvas, Paint paint) {
//        paint.setColor(Color.RED);
//        canvas.drawCircle(x, y, 50, paint);
//    }

    public void update() {
        double distanceToPlayerX = player.x - this.x;
        double distanceToPlayerY = player.y - this.y;

        double distanceToPlayer = getDistanceBetweenObjects(this, player);

        double directionX = distanceToPlayerX / distanceToPlayer;
        double directionY = distanceToPlayerY / distanceToPlayer;

        if (distanceToPlayer > 0 && distanceToPlayer < VISIBILITY){
            velocityX = (int) (directionX * MAX_SPEED);
            velocityY = (int) (directionY * MAX_SPEED);
        }else{
            velocityX = 0;
            velocityY = 0;
        }

        x += velocityX;
        y += velocityY;
    }

    private double getDistanceBetweenObjects(Enemy enemy, Player player) {
        return Math.sqrt(
                Math.pow(enemy.x - player.x,2) +
                Math.pow(enemy.y - player.y,2)
        );
    }

    @Override
    public void run() {
        while (isAlive) {
            if (Math.abs(player.x - this.x) < 5 && Math.abs(player.y - this.y) < 5){
                isAlive = false;
            }
        }
    }
}
