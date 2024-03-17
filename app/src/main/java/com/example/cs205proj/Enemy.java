package com.example.cs205proj;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Enemy extends Entity{
    int MAX_SPEED = 10;
    int VISIBILITY = 2000;
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

    public void draw(Canvas canvas, Paint paint) {
        // player is currently a circle
        paint.setColor(Color.RED);
        canvas.drawCircle(x, y, 50, paint);
    }

    public void update(Rect display) {
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
}
